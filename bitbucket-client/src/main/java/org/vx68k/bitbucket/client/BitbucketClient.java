/*
 * BitbucketClient.java
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

package org.vx68k.bitbucket.client;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.vx68k.bitbucket.Bitbucket;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketIssue;
import org.vx68k.bitbucket.BitbucketPullRequest;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUserAccount;
import org.vx68k.bitbucket.client.internal.ClientIssue;
import org.vx68k.bitbucket.client.internal.ClientRepository;
import org.vx68k.bitbucket.client.internal.ClientTeamAccount;
import org.vx68k.bitbucket.client.internal.ClientUserAccount;
import org.vx68k.bitbucket.client.util.JsonStructureMessageBodyReader;
import org.vx68k.bitbucket.client.util.JsonbMessageBodyReader;
import org.vx68k.bitbucket.client.util.OAuth2Authenticator;

/**
 * Bitbucket API client.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class BitbucketClient implements Bitbucket, Serializable
{
    private static final long serialVersionUID = 2L;

    /**
     * Base URI of the Bitbucket API.
     */
    protected static final URI API_BASE =
        URI.create("https://api.bitbucket.org/");

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
     * Regular expression pattern to match full names of repositories.
     */
    public static final Pattern REPOSITORY_FULL_NAME_REGEXP =
        Pattern.compile("^[^/]+/[^/]+$");

    /**
     * OAuth 2.0 authenticator.
     */
    private final OAuth2Authenticator oAuth2Authenticator;

    /**
     * {@link ClientBuilder} object created in the constructor.
     * This object is used to build JAX-RS {@link Client} objects.
     */
    private transient ClientBuilder clientBuilder;

    /**
     * Constructs a runtime object with a new {@link ClientBuilder} object.
     */
    public BitbucketClient()
    {
        this.oAuth2Authenticator =
            new OAuth2Authenticator(API_BASE, TOKEN_ENDPOINT_URI);
    }

    /**
     * Returns the OAuth 2 authenticator.
     *
     * @return the OAuth 2 authenticator
     */
    public final OAuth2Authenticator getOAuth2Authenticator()
    {
        return oAuth2Authenticator;
    }

    protected final ClientBuilder getClientBuilder()
    {
        synchronized (this) {
            if (clientBuilder == null) {
                JsonbBuilder jsonbBuilder = JsonbBuilder.newBuilder();
                clientBuilder = ClientBuilder.newBuilder()
                    .register(JsonStructureMessageBodyReader.class)
                    .register(new JsonbMessageBodyReader<ClientUserAccount>(jsonbBuilder))
                    .register(new JsonbMessageBodyReader<ClientTeamAccount>(jsonbBuilder))
                    .register(new JsonbMessageBodyReader<ClientRepository>(jsonbBuilder))
                    .register(oAuth2Authenticator);
            }
        }
        return clientBuilder;
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

        oAuth2Authenticator.requestAccessToken(Entity.form(form));
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

        oAuth2Authenticator.requestAccessToken(Entity.form(form));
    }

    /**
     * Logs out.
     */
    public final void logout()
    {
        oAuth2Authenticator.setRefreshToken(null);
        oAuth2Authenticator.setAccessToken(null);
        oAuth2Authenticator.setExpiration(null);
    }

    /**
     * Gets a resource.
     *
     * @param <T> the return type
     * @param base a base URI, or {@code null} for the Bitbucket Cloud REST API
     * @param runtimeType the type of the resource to be returned
     * @param mediaTypes acceptable MIME media types
     * @return a received resource, or {@code null} not found
     */
    public final <T> T get(final URI base, final Class<T> runtimeType,
        final MediaType... mediaTypes)
    {
        return get(base, null, runtimeType, mediaTypes);
    }

    /**
     * Gets a resource.
     *
     * @param <T> the return type
     * @param base a base URI, or {@code null} for the Bitbucket Cloud REST API
     * @param path a path relative to the base URI, or {@code null}
     * @param templateValues a map of template values, or {@code null}
     * @param runtimeType the type of the resource to be returned
     * @param mediaTypes acceptable MIME media types
     * @return a received resource, or {@code null} not found
     */
    public final <T> T get(URI base, final String path,
        Map<String, Object> templateValues, final Class<T> runtimeType,
        final MediaType... mediaTypes)
    {
        if (base == null) {
            base = API_BASE;
        }
        if (templateValues == null) {
            templateValues = Collections.emptyMap();
        }

        Client client = getClientBuilder().build();
        try {
            WebTarget target = client.target(base);
            if (path != null) {
                target = target.path(path);
                target = target.resolveTemplates(templateValues);
            }
            return target.request(mediaTypes).get(runtimeType);
        }
        catch (NotFoundException exception) {
            return null;
        }
        finally {
            client.close();
        }
    }

    /**
     * Gets a resource from a REST API.
     *
     * @param <T> the return type
     * @param base a base URI, or {@code null} for the Bitbucket Cloud REST API
     * @param modifier a function modifies the {@link WebTarget} instance
     * @param runtimeType the type of the resource to be returned
     * @param mediaTypes acceptable MIME media types
     * @return a received resource, or {@code null} not found
     */
    public final <T> T get(URI base, final UnaryOperator<WebTarget> modifier,
        final Class<T> runtimeType, MediaType... mediaTypes)
    {
        if (base == null) {
            base = API_BASE;
        }
        if (mediaTypes != null && mediaTypes.length == 0) {
            mediaTypes = new MediaType[] {MediaType.APPLICATION_JSON_TYPE};
        }

        Client client = getClientBuilder().build();
        try {
            WebTarget target = client.target(base);
            if (modifier != null) {
                target = modifier.apply(target);
            }
            return target.request(mediaTypes).get(runtimeType);
        }
        catch (NotFoundException exception) {
            return null;
        }
        finally {
            client.close();
        }
    }

    public final <T> List<T> getList(URI base,
        final UnaryOperator<WebTarget> modifier, final Class<? extends T> type)
    {
        if (base == null) {
            base = API_BASE;
        }

        URI uri;
        Client client = getClientBuilder().build();
        try {
            WebTarget target = client.target(base);
            if (modifier != null) {
                target = modifier.apply(target);
            }
            uri = target.getUri();
        }
        finally {
            client.close();
        }

        return new PaginatedList<>(getClientBuilder(), uri, type);
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
        Client client = getClientBuilder().build();
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
     * {@inheritDoc}
     * <p>This implementation gets the user resource remotely from Bitbucket
     * Cloud.</p>
     */
    @Override
    public final BitbucketUserAccount getUserAccount(final String name)
    {
        Map<String, Object> values = Collections.singletonMap("name", name);

        return get(null, "/2.0/users/{name}", values, ClientUserAccount.class);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation gets the team resource remotely from Bitbucket
     * Cloud.</p>
     */
    @Override
    public final BitbucketAccount getTeamAccount(final String name)
    {
        Map<String, Object> values = Collections.singletonMap("name", name);

        return get(null, "/2.0/teams/{name}", values, ClientTeamAccount.class);
    }

    @Override
    public final BitbucketRepository getRepository(final String fullName)
    {
        if (fullName != null
            && !(REPOSITORY_FULL_NAME_REGEXP.matcher(fullName).matches())) {
            throw new IllegalArgumentException("Full name is invalid");
        }

        return get(API_BASE, (target) ->
            target.path("/2.0/repositories/{fullName}")
                .resolveTemplate("fullName", fullName),
            ClientRepository.class);
    }

    @Override
    public final BitbucketPullRequest getPullRequest(final String fullName,
        final int id)
    {
        throw new UnsupportedOperationException("getPullRequest not implemented yet");
    }

    @Override
    public final BitbucketIssue getIssue(final String fullName, final int id)
    {
        if (fullName != null
            && !(REPOSITORY_FULL_NAME_REGEXP.matcher(fullName).matches())) {
            throw new IllegalArgumentException("Full name is invalid");
        }

        return get(API_BASE, (target) ->
            target.path("/2.0/repositories/{fullName}/issues/{id}")
                .resolveTemplate("fullName", fullName)
                .resolveTemplate("id", id),
            ClientIssue.class);
    }

    @Override
    public final Collection<BitbucketRepository> repositories(
        final String ownerName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final List<BitbucketIssue> getIssues(final String fullName,
        final String filter)
    {
        if (fullName != null
            && !(REPOSITORY_FULL_NAME_REGEXP.matcher(fullName).matches())) {
            throw new IllegalArgumentException("Full name invalid");
        }

        return getList(API_BASE, (target) -> {
            target = target.path("/2.0/repositories/{fullName}/issues");
            target = target.resolveTemplate("fullName", fullName);
            if (filter != null) {
                target = target.queryParam("q", filter);
            }
            return target;
        }, ClientIssue.class);
    }
}
