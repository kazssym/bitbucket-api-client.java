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

package org.vx68k.bitbucket.api.client;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.vx68k.bitbucket.api.BitbucketBranch;
import org.vx68k.bitbucket.api.BitbucketCommit;

/**
 * Branch or bookmark represented by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientBranch extends BitbucketClientObject
    implements BitbucketBranch
{
    /**
     * Type name for JSON branch objects for Git.
     */
    private static final String BRANCH = "branch";

    /**
     * Type name for JSON branch objects for Mercurial.
     */
    private static final String NAMED_BRANCH = "named_branch";

    /**
     * Type name for JSON bookmark objects for Mercurial.
     */
    private static final String BOOKMARK = "bookmark";

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
     * Constructs this branch with a JSON branch object.
     *
     * @param branchObject JSON branch object
     */
    public BitbucketClientBranch(final JsonObject branchObject)
    {
        super(branchObject);

        String type = getType();
        if (type == null || !(type.equals(BRANCH) || type.equals(NAMED_BRANCH)
                              || type.equals(BOOKMARK))) {
            throw new IllegalArgumentException("JSON object is not branch");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        JsonObject branchObject = getJsonObject();
        return branchObject.getString("name", null);
    }

    @Override
    public BitbucketCommit getTarget()
    {
        JsonObject target = getJsonObject().getJsonObject("target");

        BitbucketCommit value = null;
        if (target != null) {
            value = new BitbucketClientCommit(target/*, getBitbucketClient()*/);
        }
        return value;
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
