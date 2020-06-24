/*
 * ClientRef.java
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

import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Abstract client implementation class of {@link BitbucketRepository.Ref}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public abstract class ClientRef implements BitbucketRepository.Ref
{
    private String name;

    /**
     * Constructs a ref.
     */
    protected ClientRef()
    {
        // Nothing to do.
    }

    /**
     * Constructs a ref copying another.
     *
     * @param other another ref
     */
    protected ClientRef(final ClientRef other)
    {
        this.name = other.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        return name;
    }

    /**
     * Sets the name of the ref.
     *
     * @param name a string object for the name
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    @Override
    public final BitbucketCommit getTarget()
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
