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

import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Client implementation class of {@link BitbucketRepository.Branch} for the
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

    private List<String> mergeStrategies;

    /**
     * List of the head commits of the branch object.
     */
    private List<BitbucketCommit> heads;

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

        List<BitbucketCommit> otherHeads = other.getHeads();
        if (otherHeads != null) {
            otherHeads = new ArrayList<>(otherHeads);
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
    public final List<String> getMergeStrategies()
    {
        return mergeStrategies;
    }

    @JsonbProperty("merge_strategies")
    public final void setMergeStrategies(List<String> mergeStrategies)
    {
        if (mergeStrategies != null) {
            mergeStrategies = new ArrayList<>(mergeStrategies);
        }
        this.mergeStrategies = mergeStrategies;
    }

    /**
     * Returns the heads of this branch.
     *
     * @return the heads
     */
    @Override
    public final List<BitbucketCommit> getHeads()
    {
        return heads;
    }

    public final void setHeads(List<BitbucketCommit> heads)
    {
        if (heads != null) {
            heads = new ArrayList<>(heads);
        }
        this.heads = heads;
    }
}
