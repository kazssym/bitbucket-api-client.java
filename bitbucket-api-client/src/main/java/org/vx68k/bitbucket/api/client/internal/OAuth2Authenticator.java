/*
 * OAuth2Authenticator.java
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client.internal;

import java.io.IOException;
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
import org.vx68k.bitbucket.api.client.TokenRefreshEvent;
import org.vx68k.bitbucket.api.client.TokenRefreshListener;

/**
 * OAuth 2.0 authenticator.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public final class OAuth2Authenticator implements ClientRequestFilter
{
    /**
     * Expiry margin.
     */
    private static final Duration EXPIRY_MARGIN = Duration.ofSeconds(60);

    /**
     * URI prefix to which authenticated requests shall be sent.
     */
    private final String uriPrefix;

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
     * @param uriPrefix a value of the URI prefix (case-sensitive)
     * @param tokenEndpointUri a value of the token endpoint URI
     */
    public OAuth2Authenticator(
        final String uriPrefix, final URI tokenEndpointUri)
    {
        this.uriPrefix = uriPrefix;
        this.tokenEndpointUri = tokenEndpointUri;
        this.clientAuthenticator = new BasicAuthenticator();
        this.tokenRefreshListeners = new LinkedHashSet<>();

        if (uriPrefix == null) {
            throw new IllegalArgumentException("URI prefix must not be null");
        }
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
    public void requestToken(final Entity formEntity)
    {
        Client client = ClientBuilder.newClient();
        try {
            client.register(JsonMessageBodyReader.class);
            client.register(clientAuthenticator);

            JsonObject object = client.target(tokenEndpointUri)
                .request(MediaType.APPLICATION_JSON)
                .post(formEntity, JsonObject.class);

            setAccessToken(object.getString("access_token"));
            setRefreshToken(object.getString("refresh_token", null));

            Instant expiry = null;
            if (object.containsKey("expires_in")) {
                expiry = Instant.now()
                    .plusSeconds(object.getInt("expires_in"));
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
            Form form = new Form("grant_type", "refresh_token");
            form.param("refresh_token", refreshToken);

            requestToken(Entity.form(form));

            fireTokenRefreshed();
        }
    }

    @Override
    public void filter(final ClientRequestContext requestContext)
        throws IOException
    {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        URI uri = requestContext.getUri();

        if (accessToken != null && uri.toString().startsWith(uriPrefix)) {
            // Refresh the access token if necessary.
            if (accessTokenExpiryForTest.isBefore(Instant.now())) {
                refreshAccessToken();
            }

            headers.add("Authorization", "Bearer " + accessToken);
        }
    }
}
