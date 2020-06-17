/*
 * ClientRef.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

import javax.json.JsonObject;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.BitbucketRef;

/**
 * Abstract client implementation class of {@link BitbucketRef}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public abstract class ClientRef extends BitbucketClientObject implements BitbucketRef
{
    /**
     * Name of the {@code name} value in a JSON object.
     */
    private static final String NAME = "name";

    /**
     * Name of the {@code target} value in a JSON object.
     */
    private static final String TARGET = "target";

    /**
     * Initializes the object.
     *
     * @param jsonObject a JSON object
     */
    public ClientRef(final JsonObject jsonObject)
    {
        this(jsonObject, null);
    }

    /**
     * Initializes the object with a Bitbucket API client.
     *
     * @param jsonObject a JSON object
     * @param bitbucketClient a Bitbucket API client
     */
    public ClientRef(
        final JsonObject jsonObject, final BitbucketClient bitbucketClient)
    {
        super(jsonObject, bitbucketClient);

        String type = getType();
        if (!(BRANCH.equals(type) || NAMED_BRANCH.equals(type)
            || BOOKMARK.equals(type) || "tag".equals(type))) {
            throw new IllegalArgumentException(
                "JSON object is not branch, named_branch, bookmark or tag");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        return getJsonObject().getString(NAME, null);
    }

    @Override
    public final BitbucketCommit getTarget()
    {
        JsonObject target = getJsonObject().getJsonObject(TARGET);

        BitbucketCommit value = null;
        if (target != null) {
            value = new BitbucketClientCommit(target, getBitbucketClient());
        }
        return value;
    }
}
