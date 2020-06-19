/*
 * OAuth2Authenticator.java
 * Copyright (C) 2018-2020 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.vx68k.bitbucket.client.util;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.vx68k.bitbucket.client.TokenRefreshEvent;
import org.vx68k.bitbucket.client.TokenRefreshListener;
import org.vx68k.bitbucket.client.internal.JsonMessageBodyReader;

/**
 * OAuth 2.0 authentication filter for the JAX-RS Client API.
 * This class should support any grant types which uses a token endpoint.
 *
 * @author Kaz Nishimura
 * @see <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a>
 * @since 6.0
 */
public final class OAuth2Authenticator implements ClientRequestFilter
{
    private static final String ACCESS_TOKEN = "access_token";

    private static final String EXPIRES_IN = "expires_in";

    private static final String REFRESH_TOKEN = "refresh_token";

    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    /**
     * Expiry margin.
     */
    private static final Duration EXPIRY_MARGIN = Duration.ofSeconds(60);

    /**
     * Base URI to which authenticated requests shall be sent.
     */
    private final URI baseUri;

    /**
     * Token endpoint URI.
     */
    private final URI tokenEndpointUri;

    /**
     * Client authenticator for token request.
     */
    private final BasicAuthenticator clientAuthenticator;

    /**
     * Token refresh listeners.
     */
    private final Set<TokenRefreshListener> tokenRefreshListeners;

    /**
     * Access token.
     */
    private String accessToken = null;

    /**
     * Refresh token.
     */
    private String refreshToken = null;

    /**
     * Time when the access token expires.
     */
    private Instant accessTokenExpiry = null;

    /**
     * Time when the access token expires.
     */
    private Instant accessTokenExpiryForTest = Instant.MAX;

    /**
     * Initializes the object.
     *
     * @param baseUri a base URI
     * @param tokenEndpointUri a token endpoint URI
     */
    public OAuth2Authenticator(final URI baseUri, final URI tokenEndpointUri)
    {
        this.baseUri = baseUri;
        this.tokenEndpointUri = tokenEndpointUri;
        this.clientAuthenticator = new BasicAuthenticator(tokenEndpointUri);
        this.tokenRefreshListeners = new LinkedHashSet<>();

        if (baseUri == null) {
            throw new IllegalArgumentException("Base URI must not be null");
        }
        else if (!baseUri.isAbsolute()) {
            throw new IllegalArgumentException("Base URI must be absolute");
        }
        if (tokenEndpointUri == null) {
            throw new IllegalArgumentException(
                "Token endpoint URI must not be null");
        }
        else if (!tokenEndpointUri.isAbsolute()) {
            throw new IllegalArgumentException(
                "Token endpoint URI must be absolute");
        }
    }

    /**
     * Returns the client identifier.
     *
     * @return the client identifier
     */
    public String getClientId()
    {
        return clientAuthenticator.getUsername();
    }

    /**
     * Sets the client identifier.
     *
     * @param newValue a new value of the client identifier
     */
    public void setClientId(final String newValue)
    {
        clientAuthenticator.setUsername(newValue);
    }

    /**
     * Sets the client secret.
     *
     * @param newValue a new value of the client secret.
     */
    public void setClientSecret(final String newValue)
    {
        clientAuthenticator.setPassword(newValue);
    }

    /**
     * Returns the access token.
     *
     * @return the access token
     */
    public String getAccessToken()
    {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param newValue a new value of the access token
     */
    public void setAccessToken(final String newValue)
    {
        accessToken = newValue;
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken()
    {
        return refreshToken;
    }

    /**
     * Sets the refresh token.
     *
     * @param newValue a new value of the refresh token
     */
    public void setRefreshToken(final String newValue)
    {
        refreshToken = newValue;
    }

    /**
     * Returns the time when the access token expires.
     *
     * @return the time when the access token expires
     */
    public Instant getAccessTokenExpiry()
    {
        return accessTokenExpiry;
    }

    /**
     * Sets the time when the access token expires.
     *
     * @param newValue new value of the time when the access token expires
     */
    public void setAccessTokenExpiry(final Instant newValue)
    {
        accessTokenExpiry = newValue;

        accessTokenExpiryForTest = Instant.MAX;
        if (accessTokenExpiry != null) {
            accessTokenExpiryForTest = accessTokenExpiry.minus(EXPIRY_MARGIN);
        }
    }

    /**
     * Fires a token refreshed event.
     */
    protected void fireTokenRefreshed()
    {
        fireTokenRefreshed(new TokenRefreshEvent(this));
    }

    /**
     * Fires a token refreshed event.
     *
     * @param event a token refreshed event.
     */
    protected void fireTokenRefreshed(final TokenRefreshEvent event)
    {
        tokenRefreshListeners.forEach((l) -> l.tokenRefreshed(event));
    }

    /**
     * Adds a token refresh listener.
     *
     * @param listener a token refresh listener
     */
    public void addTokenRefreshListener(final TokenRefreshListener listener)
    {
        tokenRefreshListeners.add(listener);
    }

    /**
     * Removes a token refresh listener.
     *
     * @param listener a token refresh listener
     */
    public void removeTokenRefreshListener(final TokenRefreshListener listener)
    {
        tokenRefreshListeners.remove(listener);
    }

    /**
     * Requests an access token by posting a form entity.
     *
     * @param formEntity a form entity
     */
    public void requestAccessToken(final Entity<Form> formEntity)
    {
        Client client = ClientBuilder.newClient();
        try {
            client.register(JsonMessageBodyReader.class);
            client.register(clientAuthenticator);

            JsonObject object = client.target(tokenEndpointUri)
                .request(MediaType.APPLICATION_JSON)
                .post(formEntity, JsonObject.class);

            setAccessToken(object.getString(ACCESS_TOKEN));
            setRefreshToken(object.getString(REFRESH_TOKEN, null));

            Instant expiry = null;
            if (object.containsKey(EXPIRES_IN)) {
                expiry = Instant.now()
                    .plusSeconds(object.getInt(EXPIRES_IN));
            }
            setAccessTokenExpiry(expiry);
        }
        finally {
            client.close();
        }
    }

    /**
     * Refreshes the access token.
     */
    protected void refreshAccessToken()
    {
        // Do it only if we have a refresh token.
        if (refreshToken != null) {
            Form form = new Form("grant_type", REFRESH_TOKEN_GRANT_TYPE);
            form.param(REFRESH_TOKEN, refreshToken);

            requestAccessToken(Entity.form(form));

            fireTokenRefreshed();
        }
    }

    /**
     * Adds an {@code Authorization} HTTP header to a request when
     * authentication is required.
     * The header shall be added only if the request URI is below the base URI.
     *
     * @param requestContext a request context
     */
    @Override
    public void filter(final ClientRequestContext requestContext)
    {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        URI uri = requestContext.getUri();

        if (accessToken != null && !uri.equals(baseUri.relativize(uri))) {
            // Refresh the access token if necessary.
            synchronized (this) {
                if (accessTokenExpiryForTest.isBefore(Instant.now())) {
                    refreshAccessToken();
                }
            }

            headers.add("Authorization", "Bearer " + accessToken);
        }
    }
}
