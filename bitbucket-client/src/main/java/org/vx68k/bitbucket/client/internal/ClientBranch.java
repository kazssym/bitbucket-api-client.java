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
 * Implementation class of {@link BitbucketBranch} for the Bitbucket
 * Cloud REST API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientBranch extends ClientRef implements BitbucketBranch
{
    public static final String BRANCH = "branch";

    public static final String NAMED_BRANCH = "named_branch";

    public static final String BOOKMARK = "bookmark";

    /**
     * Type of the branch object.
     */
    private String type;

    private String defaultMergeStrategy;

    private String[] mergeStrategies;

    /**
     * List of the head commits of the branch object.
     */
    private ClientCommit[] heads;

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
        this.defaultMergeStrategy = other.defaultMergeStrategy;

        setMergeStrategies(other.mergeStrategies);
        setHeads(other.heads);
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
            && !(type.equals(BRANCH)
                || type.equals(NAMED_BRANCH)
                || type.equals(BOOKMARK))) {
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
        return Arrays.copyOf(mergeStrategies, mergeStrategies.length);
    }

    @JsonbProperty("merge_strategies")
    public final void setMergeStrategies(String[] mergeStrategies)
    {
        if (mergeStrategies != null) {
            mergeStrategies =
                Arrays.copyOf(mergeStrategies, mergeStrategies.length);
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
        if (heads != null) {
            return Arrays.copyOf(heads, heads.length, BitbucketCommit[].class);
        }
        return null;
    }

    public final void setHeads(ClientCommit[] heads)
    {
        if (heads != null) {
            heads = Arrays.stream(heads).map(ClientCommit::new)
                .toArray(ClientCommit[]::new);
        }
        this.heads = heads;
    }
}
