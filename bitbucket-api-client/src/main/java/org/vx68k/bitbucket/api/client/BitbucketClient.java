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

package org.vx68k.bitbucket.api.client;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.vx68k.bitbucket.api.Bitbucket;
import org.vx68k.bitbucket.api.BitbucketAccount;
import org.vx68k.bitbucket.api.BitbucketBranch;
import org.vx68k.bitbucket.api.BitbucketCommit;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.internal.JsonMessageBodyReader;

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
    public static final String API_BASE = "https://api.bitbucket.org/2.0/";

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
     * Constructs this object with a new {@link ClientBuilder} object.
     */
    public BitbucketClient()
    {
        clientBuilder = ClientBuilder.newBuilder();
        clientBuilder.register(JsonMessageBodyReader.class);
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
     * @param value {@link BitbucketClient} object
     */
    public static void setDefaultInstance(final BitbucketClient value)
    {
        defaultInstance = value;
    }

    /**
     * Creates a {@link BitbucketUser} object from a JSON object of a user or a
     * team associating the default {@link BitbucketClient} object.
     *
     * @param object JSON object, or {@code null}
     * @return new {@link BitbucketUser} object unless the value of {@code
     * object} is {@code null}; {@code null} otherwise
     * @exception IllegalArgumentException if {@code object} is not of a user
     */
    public static BitbucketUser createUser(final JsonObject object)
    {
        return createUser(object, getDefaultInstance());
    }

    /**
     * Creates a {@link BitbucketUser} object from a JSON object of a user or a
     * team.
     *
     * @param object JSON object, or {@code null}
     * @param client Bitbucket API client, or {@code null}
     * @return new {@link BitbucketUser} object unless the value of {@code
     * object} is {@code null}; {@code null} otherwise
     * @exception IllegalArgumentException if {@code object} is not of a user
     */
    public static BitbucketUser createUser(final JsonObject object,
        final BitbucketClient client)
    {
        BitbucketUser value = null;
        if (object != null) {
            value = new BitbucketClientUser(object, client);
        }
        return value;
    }

    /**
     * Creates a {@link BitbucketAccount} instance from a JSON object with the
     * default Bitbucket client.
     *
     * @param object JSON object of a team, or {@code null}
     * @return {@link BitbucketAccount} instance
     * @exception IllegalArgumentException if {@code object} is not of a team
     */
    public static BitbucketAccount createTeam(final JsonObject object)
    {
        return createTeam(object, getDefaultInstance());
    }

    /**
     * Creates a {@link BitbucketAccount} instance from a JSON object.
     *
     * @param object JSON object of a team, or {@code null}
     * @param client Bitbucket API client, or {@code null}
     * @return {@link BitbucketAccount} instance
     * @exception IllegalArgumentException if {@code object} is not of a team
     */
    public static BitbucketAccount createTeam(
        final JsonObject object, final BitbucketClient client)
    {
        BitbucketAccount value = null;
        if (object != null) {
            value = new BitbucketClientAccount(object, client);
        }
        return value;
    }

    /**
     * Creates a {@link BitbucketRepository} object from a JSON object.
     *
     * @param object JSON object, or {@code null}
     * @return new {@link BitbucketRepository} object
     * @exception IllegalArgumentException if {@code object} is not of a
     * repository
     */
    public static BitbucketRepository createRepository(final JsonObject object)
    {
        return createRepository(object, getDefaultInstance());
    }

    /**
     * Creates a {@link BitbucketRepository} object from a JSON object and a
     * Bitbucket API client.
     *
     * @param object JSON object, or {@code null}
     * @param client Bitbucket API client, or {@code null}
     * @return new {@link BitbucketRepository} object
     * @exception IllegalArgumentException if {@code object} is not of a
     * repository
     */
    public static BitbucketRepository createRepository(
        final JsonObject object, final BitbucketClient client)
    {
        BitbucketRepository value = null;
        if (object != null) {
            value = new BitbucketClientRepository(object, client);
        }
        return value;
    }

    /**
     * Creates a {@link BitbucketBranch} object from a JSON object that
     * represents a branch or bookmark.
     *
     * @param object JSON object, or {@code null}
     * @return new {@link BitbucketBranch} object, or {@code null} if the given
     * JSON object is {@code null}
     */
    public static BitbucketBranch createBranch(final JsonObject object)
    {
        BitbucketBranch value = null;
        if (object != null) {
            value = new BitbucketClientBranch(object);
        }
        return value;
    }

    /**
     * Creates a {@link BitbucketCommit} object from a JSON object that
     * represents a commit.
     *
     * @param object JSON object, or {@code null}
     * @return new {@link BitbucketCommit} object, or {@code null} if the given
     * JSON object is {@code null}
     */
    public static BitbucketCommit createCommit(final JsonObject object)
    {
        BitbucketCommit value = null;
        if (object != null) {
            value = new BitbucketClientCommit(object);
        }
        return value;
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
            WebTarget target = client.target(API_BASE).path(path);
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
}
