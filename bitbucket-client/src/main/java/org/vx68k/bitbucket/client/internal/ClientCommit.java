/*
 * ClientCommit.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client.internal;

import javax.json.JsonObject;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.client.BitbucketClient;
import org.vx68k.bitbucket.client.BitbucketClientObject;

/**
 * Client implementation class of {@link BitbucketCommit}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientCommit extends BitbucketClientObject implements
    BitbucketCommit
{
    /**
     * Type name for JSON commit objects.
     */
    private static final String COMMIT = "commit";

    /**
     * Initializes the object without a Bitbucket API client.
     *
     * @param jsonObject a JSON object for a commit resource
     * @see #BitbucketClientCommit(JsonObject, BitbucketClient)
     */
    public ClientCommit(final JsonObject jsonObject)
    {
        this(jsonObject, null);
    }

    /**
     * Initializes the object with a Bitbucket API client.
     *
     * @param jsonObject a JSON object for a commit resource
     * @param bitbucketClient a Bitbucket API client
     */
    public ClientCommit(
        final JsonObject jsonObject, final BitbucketClient bitbucketClient)
    {
        super(jsonObject, bitbucketClient);

        String type = getType();
        if (!COMMIT.equals(type)) {
            throw new IllegalArgumentException(
                "JSON object type is not commit");
        }
    }
}
