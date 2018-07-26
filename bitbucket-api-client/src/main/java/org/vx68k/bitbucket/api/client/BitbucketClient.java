/*
 * BitbucketClient.java - class BitbucketClient
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.vx68k.bitbucket.api.BitbucketBranch;
import org.vx68k.bitbucket.api.BitbucketCommit;
import org.vx68k.bitbucket.api.BitbucketIssueTracker;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.internal.JsonMessageBodyReader;

/**
 * Bitbucket client.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClient implements Serializable
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
    public static BitbucketClient getDefault()
    {
        return defaultInstance;
    }

    /**
     * Sets the default {@link BitbucketClient} object.
     *
     * @param value {@link BitbucketClient} object
     */
    public static void setDefault(final BitbucketClient value)
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
     */
    public static BitbucketUser createUser(final JsonObject object)
    {
        return createUser(object, getDefault());
    }

    /**
     * Creates a {@link BitbucketUser} object from a JSON object of a user or a
     * team.
     *
     * @param object JSON object, or {@code null}
     * @param client {@link BitbucketClient} object to associate, or {@code
     * null}
     * @return new {@link BitbucketUser} object unless the value of {@code
     * object} is {@code null}; {@code null} otherwise
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
     * Creates a {@link BitbucketRepository} object from a JSON object that
     * represents a repository.
     *
     * @param object JSON object, or {@code null}
     * @return new {@link BitbucketRepository} object, or {@code null} if the
     * given JSON object is {@code null}
     */
    public static BitbucketRepository createRepository(final JsonObject object)
    {
        BitbucketRepository value = null;
        if (object != null) {
            value = new BitbucketClientRepository(object);
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
     * Returns a {@link BitbucketUser} object for a user.
     *
     * @param name user name
     * @return {@link BitbucketUser} object
     */
    public BitbucketUser getUser(final String name)
    {
        Client client = clientBuilder.build();
        try {
            WebTarget base = client.target(API_BASE);
            WebTarget path = base.path("users/{name}");
            JsonObject object = path.resolveTemplate("name", name)
                .request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            return createUser(object, this);
        }
        catch (NotFoundException exception) {
            return null;
        }
        finally {
            client.close();
        }
    }

    /**
     * Returns a {@link BitbucketUser} object for a team.
     *
     * @param name team name
     * @return {@link BitbucketUser} object
     */
    public BitbucketUser getTeam(final String name)
    {
        Client client = clientBuilder.build();
        try {
            WebTarget base = client.target(API_BASE);
            WebTarget path = base.path("teams/{name}");
            JsonObject object = path.resolveTemplate("name", name)
                .request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            return createUser(object, this);
        }
        catch (NotFoundException exception) {
            return null;
        }
        finally {
            client.close();
        }
    }

    /**
     * Returns the issue tracker for a repository.
     *
     * @param name repository name
     * @return the issue tracker
     */
    public static BitbucketIssueTracker getIssueTracker(final String name)
    {
        // @todo Implement this method.
        return null;
    }
}
