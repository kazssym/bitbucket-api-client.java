/*
 * BitbucketClientIssue.java
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

import java.time.Instant;
import java.util.function.Function;
import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketIssue;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 *
 * @author Kaz Nishimura
 */
public class BitbucketClientIssue extends BitbucketClientObject implements
    BitbucketIssue
{
    /**
     * Type name for issues.
     */
    private static final String ISSUE_TYPE = "issue";

    /**
     * Initializes the object from a JSON object.
     * This constructor sets the Bitbucket API client to {@code null}.
     *
     * @param jsonObject a JSON object
     */
    public BitbucketClientIssue(final JsonObject jsonObject)
    {
        this(jsonObject, null);
    }

    /**
     * Initializes the object from a JSON object.
     *
     * @param jsonObject a JSON object
     * @param bitbucketClient a Bitbucket API client
     */
    public BitbucketClientIssue(
        final JsonObject jsonObject, final BitbucketClient bitbucketClient)
    {
        super(jsonObject, bitbucketClient);

        String type = getType();
        if (!ISSUE_TYPE.equals(type)) {
            throw new IllegalArgumentException("Object is not of an issue");
        }
    }

    /**
     * Returns a function to create an issue form a JSON object.
     *
     * @return a function to create an issue from a JSON object
     */
    public static Function<JsonObject, BitbucketClientIssue> creator()
    {
        return creator(null);
    }

    /**
     * Returns a function to create an issue form a JSON object.
     *
     * @param bitbucketClient a Bitbucket API client
     * @return a function to create an issue from a JSON object
     */
    public static Function<JsonObject, BitbucketClientIssue> creator(
        final BitbucketClient bitbucketClient)
    {
        return (jsonObject) ->
            new BitbucketClientIssue(jsonObject, bitbucketClient);
    }

    @Override
    public final BitbucketRepository getRepository()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final int getId()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final BitbucketUser getReporter()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final String getTitle()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final String getState()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Instant getCreated()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Instant getEdited()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Instant getUpdated()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
