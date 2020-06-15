/*
 * BitbucketClient.java
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

package org.vx68k.bitbucket.client;

import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.vx68k.bitbucket.Bitbucket;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.client.internal.JsonMessageBodyReader;
import org.vx68k.bitbucket.client.internal.OAuth2Authenticator;

/**
 * Bitbucket API client.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClient implements Bitbucket, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Base URI of the Bitbucket API.
     */
    protected static final URI API_BASE_URI =
        URI.create("https://api.bitbucket.org/2.0/");

    /**
     * Authorization endpoint URI.
     */
    public static final URI AUTHORIZATION_ENDPOINT_URI =
        URI.create("https://bitbucket.org/site/oauth2/authorize");

    /**
     * Token endpoint URI.
     */
    public static final URI TOKEN_ENDPOINT_URI =
        URI.create("https://bitbucket.org/site/oauth2/access_token");

    /**
     * Default {@link BitbucketClient} object.
     */
    private static BitbucketClient defaultInstance = new BitbucketClient();

    /**
     * {@link ClientBuilder} object created in the constructor.
     * This object is used to build JAX-RS {@link Client} objects.
     */
    private final ClientBuilder clientBuilder;

    /**
     * OAuth 2.0 authenticator.
     */
    private final OAuth2Authenticator authenticator;

    /**
     * Constructs this object with a new {@link ClientBuilder} object.
     */
    public BitbucketClient()
    {
        this.clientBuilder = ClientBuilder.newBuilder();
        this.authenticator =
            new OAuth2Authenticator(API_BASE_URI, TOKEN_ENDPOINT_URI);

        this.clientBuilder.register(JsonMessageBodyReader.class);
        this.clientBuilder.register(authenticator);
    }

    /**
     * Returns the default {@link BitbucketClient} object.
     *
     * @return the default {@link BitbucketClient} object
     */
    public static BitbucketClient getDefaultInstance()
    {
        return defaultInstance;
    }

    /**
     * Sets the default {@link BitbucketClient} object.
     *
     * @param newValue {@link BitbucketClient} object
     */
    public static void setDefaultInstance(final BitbucketClient newValue)
    {
        defaultInstance = newValue;
    }

    /**
     * Returns the client identifier for OAuth.
     *
     * @return the client identifier
     */
    public final String getClientId()
    {
        return authenticator.getClientId();
    }

    /**
     * Sets the client identifier for OAuth.
     *
     * @param newValue a new value of the client identifier
     */
    public final void setClientId(final String newValue)
    {
        authenticator.setClientId(newValue);
    }

    /**
     * Sets the client secret for OAuth.
     *
     * @param newValue a new value of the client secret.
     */
    public final void setClientSecret(final String newValue)
    {
        authenticator.setClientSecret(newValue);
    }

    /**
     * Returns the access token.
     *
     * @return the access token
     */
    public final String getAccessToken()
    {
        return authenticator.getAccessToken();
    }

    /**
     * Sets the access token.
     *
     * @param newValue new value of the access token
     */
    public final void setAccessToken(final String newValue)
    {
        authenticator.setAccessToken(newValue);
    }

    /**
     * Returns the refresh token.
     *
     * @return the refresh token
     */
    public final String getRefreshToken()
    {
        return authenticator.getRefreshToken();
    }

    /**
     * Sets the refresh token.
     *
     * @param newValue a new value of the refresh token
     */
    public final void setRefreshToken(final String newValue)
    {
        authenticator.setRefreshToken(newValue);
    }

    /**
     * Returns the time when the access token expires.
     *
     * @return the time when the access token expires
     */
    public final Instant getAccessTokenExpiry()
    {
        return authenticator.getAccessTokenExpiry();
    }

    /**
     * Sets the time when the access token expires.
     *
     * @param newValue new value of the time when the access token expires
     */
    public final void setAccessTokenExpiry(final Instant newValue)
    {
        authenticator.setAccessTokenExpiry(newValue);
    }

    /**
     * Adds a token refresh listener.
     *
     * @param listener a token refresh listener
     */
    public final void addTokenRefreshListener(
        final TokenRefreshListener listener)
    {
        authenticator.addTokenRefreshListener(listener);
    }

    /**
     * Removes a token refresh listener.
     *
     * @param listener a token refresh listener
     */
    public final void removeTokenRefreshListener(
        final TokenRefreshListener listener)
    {
        authenticator.removeTokenRefreshListener(listener);
    }

    /**
     * Logs in with an authorization code.
     *
     * @param code an authorization code
     * @param redirectionUri a redirection URI
     */
    public final void loginWithAuthorizationCode(
        final String code, final URI redirectionUri)
    {
        Form form = new Form("grant_type", "authorization_code");
        form.param("code", code);
        if (redirectionUri != null) {
            form.param("redirect_uri", redirectionUri.toString());
        }

        authenticator.requestAccessToken(Entity.form(form));
    }

    /**
     * Logs in with resource owner password credentials.
     *
     * @param username a username
     * @param password a password
     */
    public final void login(final String username, final String password)
    {
        Form form = new Form("grant_type", "password");
        form.param("username", username);
        form.param("password", password);

        authenticator.requestAccessToken(Entity.form(form));
    }

    /**
     * Logs out.
     */
    public final void logout()
    {
        authenticator.setRefreshToken(null);
        authenticator.setAccessToken(null);
        authenticator.setAccessTokenExpiry(null);
    }

    /**
     * Gets a JSON object by a link.
     *
     * @param link the URI for a link
     * @return a JSON object if one was found; {@code null} otherwise
     */
    public final JsonObject get(final URI link)
    {
        String[] mediaTypes = new String[] {MediaType.APPLICATION_JSON};
        return get(link, mediaTypes, JsonObject.class);
    }

    /**
     * Gets a media object by a link.
     *
     * @param <T> return type
     * @param link the URI for a link
     * @param mediaTypes acceptable MIME media types
     * @param type the type of the media object to return
     * @return media object if one was found; {@code null} otherwise
     */
    public final <T> T get(
        final URI link, final String[] mediaTypes, final Class<T> type)
    {
        Client client = clientBuilder.build();
        try {
            WebTarget target = client.target(link);
            return target.request().accept(mediaTypes).get(type);
        }
        catch (NotFoundException exception) {
            return null;
        }
        finally {
            client.close();
        }
    }

    /**
     * Makes a (@code POST} request to a URI.
     *
     * @param uri a URI
     * @param entity an entity to post
     * @return a JSON object
     */
    public final JsonObject post(final URI uri, final Entity<?> entity)
    {
        Client client = clientBuilder.build();
        try {
            return client.target(uri).request()
                .accept(MediaType.APPLICATION_JSON)
                .post(entity, JsonObject.class);
        }
        finally {
            client.close();
        }
    }

    /**
     * Gets a JSON object from a resource.
     *
     * @param path path of the resource with templates
     * @param values template values, or {@code null}
     * @return JSON object if found, {@code null} otherwise
     */
    public final JsonObject getResource(
        final String path, final Map<String, Object> values)
    {
        Client client = clientBuilder.build();
        try {
            WebTarget target = client.target(API_BASE_URI).path(path);
            if (values != null) {
                target = target.resolveTemplates(values);
            }
            return target.request()
                .accept(MediaType.APPLICATION_JSON).get(JsonObject.class);
        }
        catch (NotFoundException exception) {
            return null;
        }
        finally {
            client.close();
        }
    }

    /**
     * {@inheritDoc}
     * <p>This implementation gets the user resource remotely from Bitbucket
     * Cloud.</p>
     */
    @Override
    public final BitbucketAccount getUser(final String name)
    {
        Map<String, Object> values = Collections.singletonMap("name", name);

        JsonObject object = getResource("/users/{name}", values);
        BitbucketAccount value = null;
        if (object != null) {
            value = new BitbucketClientUser(object, this);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation gets the team resource remotely from Bitbucket
     * Cloud.</p>
     */
    @Override
    public final BitbucketAccount getTeam(final String name)
    {
        Map<String, Object> values = Collections.singletonMap("name", name);

        JsonObject object = getResource("/teams/{name}", values);
        BitbucketAccount value = null;
        if (object != null) {
            value = new BitbucketClientAccount(object, this);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation gets the repository resource remotely from
     * Bitbucket Cloud.</p>
     */
    @Override
    public final BitbucketRepository getRepository(
        final String ownerName, final String name)
    {
        Map<String, Object> values = new HashMap<>();
        values.put("owner", ownerName);
        values.put("name", name);

        JsonObject object = getResource(
            "/repositories/{owner}/{name}", values);
        BitbucketRepository value = null;
        if (object != null) {
            value = new BitbucketClientRepository(object, this);
        }
        return value;
    }

    @Override
    public final Collection<BitbucketRepository> repositories(
        final String ownerName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}