/*
 * ClientBranch.java
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

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketBranch;

/**
 * Client implementation class of {@link BitbucketBranch}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientBranch extends ClientRef implements BitbucketBranch
{
    /**
     * Type of the branch object.
     */
    @JsonbProperty("type")
    private String type;

    /**
     * List of the head commits of the branch object.
     */
    @JsonbProperty("heads")
    private List<BitbucketClientCommit> heads;

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
     * Constructs a branch object.
     */
    public ClientBranch()
    {
        // Nothing to do.
    }

    /**
     * Constructs a branch copyting another
     * @param other another branch
     */
    public ClientBranch(final ClientBranch other)
    {
        this.type = other.type;
        this.heads = other.heads; // TODO: Make a copy.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getType()
    {
        return type;
    }

    /**
     * Sets the type of the branch object.
     *
     * @param type a string object for the type
     */
    public final void setType(final String type)
    {
        this.type = type;
    }

    /**
     * Returns the heads of this branch.
     *
     * @return the heads
     */
    public final List<BitbucketClientCommit> getHeads()
    {
        return heads;
    }

    public final void setHeads(final List<BitbucketClientCommit> heads)
    {
        this.heads = heads; // TODO: Make a copy.
    }
}
