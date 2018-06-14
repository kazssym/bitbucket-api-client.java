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
import org.vx68k.bitbucket.api.BitbucketIssueTracker;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Bitbucket client.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClient
{
    /**
     * Does nothing but denies direct instantiation.
     */
    protected BitbucketClient()
    {
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
            return new BitbucketClientUser(object);
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
        BitbucketRepository user = null;
        if (object != null) {
            return new BitbucketClientRepository(object);
        }
        return user;
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
