/*
 * ClientCommit.java
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

import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketCommit;

/**
 * Client implementation class of {@link BitbucketCommit}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientCommit implements BitbucketCommit
{
    @JsonbProperty("hash")
    private String hash;

    /**
     * Constructs a commit.
     */
    public ClientCommit()
    {
        // Nothing to do.
    }

    /**
     * Construct a commit copying another.
     *
     * @param other another commit
     */
    public ClientCommit(final ClientCommit other)
    {
        this.hash = other.hash;
    }

    @JsonbProperty("type")
    private final String getType()
    {
        return "commit";
    }

    public final String getHash()
    {
        return hash;
    }

    public final void setHash(final String hash)
    {
        this.hash = hash;
    }
}
