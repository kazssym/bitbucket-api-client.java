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

import javax.json.JsonObject;
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
public class BitbucketClient
{
    /**
     * Base URI of the Bitbucket API.
     */
    public static final String API_BASE = "https://api.bitbucket.org/2.0/";

    /**
     * Default {@BitbucketClient} object.
     */
    private static BitbucketClient defaultClient = new BitbucketClient();

    /**
     * Allows custom implementations to extend this class.
     */
    protected BitbucketClient()
    {
    }

    /**
     * Returns the default {@link BitbucketClient} object.
     *
     * @return the default {@link BitbucketClient} object
     */
    public static BitbucketClient getDefaultClient()
    {
        return defaultClient;
    }

    /**
     * Returns a new {@link BitbucketClient} object.
     *
     * @return new {@link BitbucketClient} object
     */
    public static BitbucketClient newClient()
    {
        return new BitbucketClient();
    }

    /**
     * Creates a {@link BitbucketUser} object from a JSON object that
     * represents a user or team.
     *
     * @param object JSON object, or {@code null}
     * @return new {@link BitbucketUser} object, or {@code null} if the given
     * JSON object is {@code null}
     */
    public static BitbucketUser createUser(final JsonObject object)
    {
        BitbucketUser user = null;
        if (object != null) {
            user = new BitbucketClientUser(object);
        }
        return user;
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
        BitbucketRepository repository = null;
        if (object != null) {
            repository = new BitbucketClientRepository(object);
        }
        return repository;
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
        BitbucketBranch branch = null;
        if (object != null) {
            branch = new BitbucketClientBranch(object);
        }
        return branch;
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
        BitbucketCommit commit = null;
        if (object != null) {
            commit = new BitbucketClientCommit(object);
        }
        return commit;
    }

    /**
     * Returns a {@link BitbucketUser} object from Bitbucket Cloud.
     *
     * @param userName name of the user
     * @return {@link BitbucketUser} object
     */
    public static BitbucketUser getUser(final String userName)
    {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(API_BASE).path("users/{user}");
            JsonObject object = target.resolveTemplate("user", userName)
                .register(JsonMessageBodyReader.class)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
            return createUser(object);
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
