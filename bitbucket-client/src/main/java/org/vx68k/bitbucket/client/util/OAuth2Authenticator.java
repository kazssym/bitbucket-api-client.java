/*
 * OAuth2Authenticator.java
 * Copyright (C) 2018-2020 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 * OAuth 2.0 authentication filter for the JAX-RS Client API.
 * This class should support any grant types which uses a token endpoint.
 *
 * @author Kaz Nishimura
 * @see <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a>
 * @since 6.0
 */
public class OAuth2Authenticator extends BearerAuthenticator
{
    private static final String ACCESS_TOKEN = "access_token";

    private static final String EXPIRES_IN = "expires_in";

    private static final String REFRESH_TOKEN = "refresh_token";

    private static final String GRANT_TYPE = "grant_type";

    private static final String REFRESH_TOKEN_GRANT = "refresh_token";

    /**
     * Expiry margin.
     */
    private static final Duration EXPIRY_MARGIN = Duration.ofSeconds(60);

    /**
     * Token endpoint URI.
     */
    private final URI tokenEndpointUri;

    /**
     * Client authenticator for token request.
     */
    private final BasicAuthenticator clientAuthenticator;

    /**
     * Time when the access token expires.
     */
    private Instant expiration = null;

    /**
     * Time when the access token expires.
     */
    private Instant accessTokenRefresh = Instant.MAX;

    /**
     * Refresh token.
     */
    private String refreshToken = null;

    /**
     * Token refresh listeners.
     */
    private final Set<TokenRefreshListener> tokenRefreshListeners =
        new LinkedHashSet<>();

    /**
     * Initializes the object.
     *
     * @param baseUri a base URI
     * @param tokenEndpointUri a token endpoint URI
     */
    public OAuth2Authenticator(final URI baseUri, final URI tokenEndpointUri)
    {
        super(baseUri);

        if (tokenEndpointUri == null) {
            throw new IllegalArgumentException("Token endpoint URI is null");
        }
        else if (!tokenEndpointUri.isAbsolute()) {
            throw new IllegalArgumentException("Token endpoint URI is not absolute");
        }

        this.tokenEndpointUri = tokenEndpointUri;
        this.clientAuthenticator =
            new BasicAuthenticator(tokenEndpointUri.resolve("/"));
    }

    /**
     * Returns the client identifier.
     *
     * @return the client identifier
     */
    public final String getClientId()
    {
        return clientAuthenticator.getUsername();
    }

    /**
     * Sets the client identifier.
     *
     * @param newValue a new value of the client identifier
     */
    public final void setClientId(final String newValue)
    {
        clientAuthenticator.setUsername(newValue);
    }

    /**
     * Sets the client secret.
     *
     * @param newValue a new value of the client secret.
     */
    public final void setClientSecret(final String newValue)
    {
        clientAuthenticator.setPassword(newValue);
    }

    /**
     * Returns the time when the access token expires.
     *
     * @return the time when the access token expires
     */
    public final Instant getExpiration()
    {
        return expiration;
    }

    /**
     * Sets the time when the access token expires.
     *
     * @param expiration new value of the time when the access token expires
     */
    public final void setExpiration(final Instant expiration)
    {
        this.expiration = expiration;

        accessTokenRefresh = Instant.MAX;
        if (expiration != null) {
            accessTokenRefresh = expiration.minus(EXPIRY_MARGIN);
        }
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token
     */
    public final String getRefreshToken()
    {
        return refreshToken;
    }

    /**
     * Sets the refresh token.
     *
     * @param refreshToken a new value of the refresh token
     */
    public final void setRefreshToken(final String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    /**
     * Fires a token refreshed event.
     */
    protected final void fireTokenRefreshed()
    {
        fireTokenRefreshed(new TokenRefreshEvent(this));
    }

    /**
     * Fires a token refreshed event.
     *
     * @param event a token refreshed event.
     */
    protected final void fireTokenRefreshed(final TokenRefreshEvent event)
    {
        tokenRefreshListeners.forEach((l) -> l.tokenRefreshed(event));
    }

    /**
     * Adds a token refresh listener.
     *
     * @param listener a token refresh listener
     */
    public final void addTokenRefreshListener(final TokenRefreshListener listener)
    {
        tokenRefreshListeners.add(listener);
    }

    /**
     * Removes a token refresh listener.
     *
     * @param listener a token refresh listener
     */
    public final void removeTokenRefreshListener(final TokenRefreshListener listener)
    {
        tokenRefreshListeners.remove(listener);
    }

    /**
     * Requests an access token by posting a form entity.
     *
     * @param entity a form entity
     */
    public final void requestAccessToken(final Entity<Form> entity)
    {
        // Client is not {@link AutoCloseable}.
        Client client = ClientBuilder.newClient()
            .register(new JsonStructureMessageBodyReader())
            .register(clientAuthenticator);
        try {
            JsonObject object = client.target(tokenEndpointUri)
                .request(MediaType.APPLICATION_JSON)
                .post(entity, JsonObject.class);

            setAccessToken(object.getString(ACCESS_TOKEN));
            setRefreshToken(object.getString(REFRESH_TOKEN, null));

            if (object.containsKey(EXPIRES_IN)) {
                setExpiration(Instant.now()
                    .plusSeconds(object.getInt(EXPIRES_IN)));
            }
            else {
                setExpiration(null);
            }
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
    }

    /**
     * Refreshes the access token if necessary.
     */
    @Override
    protected final void validateAccessToken()
    {
        super.validateAccessToken();

        synchronized (this) {
            if (accessTokenRefresh.isBefore(Instant.now()) && refreshToken != null) {
                Form form = new Form()
                    .param(GRANT_TYPE, REFRESH_TOKEN_GRANT)
                    .param(REFRESH_TOKEN, refreshToken);
                requestAccessToken(Entity.form(form));

                fireTokenRefreshed();
            }
        }
    }
}
