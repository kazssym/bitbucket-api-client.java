/*
 * ClientBranch.java
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

import java.util.Arrays;
import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketCommit;

/**
 * Client implementation class of {@link BitbucketBranch} for the
 * {@code "branch"}, {@code "named_branch"} and {@code "bookmark"} objects.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientBranch extends ClientRef implements BitbucketBranch
{
    /**
     * Type of the branch object.
     */
    private String type;

    private String defaultMergeStrategy;

    private String[] mergeStrategies;

    /**
     * List of the head commits of the branch object.
     */
    private BitbucketCommit[] heads;

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
    public ClientBranch(final BitbucketBranch other)
    {
        this.type = other.getType();

        BitbucketCommit[] otherHeads = other.getHeads();
        if (otherHeads != null) {
            otherHeads = Arrays.stream(otherHeads)
                .map((i) -> (ClientCommit)i)
                .map(ClientCommit::new)
                .toArray(BitbucketCommit[]::new);
        }
        this.heads = otherHeads;
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
        if (type != null
            && !(type.equals("branch")
                || type.equals("named_branch")
                || type.equals("bookmark"))) {
            throw new IllegalArgumentException("Type is not of branch objects");
        }
        this.type = type;
    }

    @JsonbProperty("default_merge_strategy")
    @Override
    public final String getDefaultMergeStrategy()
    {
        return defaultMergeStrategy;
    }

    @JsonbProperty("default_merge_strategy")
    public final void setDefaultMergeStrategy(final String defaultMergeStrategy)
    {
        this.defaultMergeStrategy = defaultMergeStrategy;
    }

    @JsonbProperty("merge_strategies")
    @Override
    public final String[] getMergeStrategies()
    {
        return mergeStrategies;
    }

    @JsonbProperty("merge_strategies")
    public final void setMergeStrategies(String[] mergeStrategies)
    {
        if (mergeStrategies != null) {
            mergeStrategies = Arrays.stream(mergeStrategies)
                .toArray(String[]::new);
        }
        this.mergeStrategies = mergeStrategies;
    }

    /**
     * Returns the heads of this branch.
     *
     * @return the heads
     */
    @Override
    public final BitbucketCommit[] getHeads()
    {
        return heads;
    }

    public final void setHeads(BitbucketCommit[] heads)
    {
        if (heads != null) {
            heads = Arrays.stream(heads)
                .map((i) -> (ClientCommit)i)
                .map(ClientCommit::new)
                .toArray(BitbucketCommit[]::new);
        }
        this.heads = heads;
    }
}
