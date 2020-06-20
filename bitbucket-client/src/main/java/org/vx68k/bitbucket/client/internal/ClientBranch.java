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

package org.vx68k.bitbucket.client.internal;

import java.util.ArrayList;
import java.util.List;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketCommit;

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
    private String type;

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
        this.heads = new ArrayList<>(other.getHeads());
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
    public final List<BitbucketCommit> getHeads()
    {
        return heads;
    }

    public final void setHeads(final List<? extends BitbucketCommit> heads)
    {
        this.heads = new ArrayList<>(heads);
    }
}
