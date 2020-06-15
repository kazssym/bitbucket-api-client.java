/*
 * BitbucketClientBranch.java - class BitbucketClientBranch
 * Copyright (C) 2015-2018 Kaz Nishimura
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

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.vx68k.bitbucket.BitbucketBranch;

/**
 * Client implementation of {@link BitbucketBranch}.
 * This class represents a branch or bookmark by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientBranch extends BitbucketClientRef implements
    BitbucketBranch
{
    /**
     * Parses a JSON array for commits.
     *
     * @param commitsArray JSON array of commits
     * @return list of commits
     */
    protected static List<BitbucketClientCommit> parseCommits(
        final JsonArray commitsArray)
    {
        List<BitbucketClientCommit> commits = null;
        if (commitsArray != null) {
            commits = new ArrayList<>();
            for (JsonValue value : commitsArray) {
                commits.add(new BitbucketClientCommit((JsonObject) value));
            }
        }
        return commits;
    }

    /**
     * Initializes the object.
     *
     * @param jsonObject a JSON object
     */
    public BitbucketClientBranch(final JsonObject jsonObject)
    {
        this(jsonObject, null);
    }

    /**
     * Initializes the object with a Bitbucket API client.
     *
     * @param jsonObject a JSON object
     * @param bitbucketClient a Bitbucket API client
     */
    public BitbucketClientBranch(
        final JsonObject jsonObject, final BitbucketClient bitbucketClient)
    {
        super(jsonObject, bitbucketClient);

        String type = getType();
        if (!(BRANCH.equals(type) || NAMED_BRANCH.equals(type)
            || BOOKMARK.equals(type))) {
            throw new IllegalArgumentException(
                "JSON object is not branch, named_branch or bookmark");
        }
    }

    /**
     * Returns the heads of this branch.
     *
     * @return the heads
     */
    public final List<BitbucketClientCommit> getHeads()
    {
        JsonObject branchObject = getJsonObject();
        List<BitbucketClientCommit> heads = null;
        if (branchObject.containsKey("heads")) {
            heads = parseCommits(branchObject.getJsonArray("heads"));
        }
        return heads;
    }
}
