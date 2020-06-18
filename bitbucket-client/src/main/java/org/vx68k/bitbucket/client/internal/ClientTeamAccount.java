/*
 * ClientTeamAccount.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

import java.util.Objects;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbProperty;

/**
 * Client implementation class of {@link BitbucketAccount} for a team account.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientTeamAccount extends ClientAccount
{
    /**
     * Constructs a team account.
     */
    public ClientTeamAccount()
    {
        // Nothing to do.
    }

    /**
     * Constructs a team account copying another.
     *
     * @param other another team account
     */
    public ClientTeamAccount(final ClientTeamAccount other)
    {
        super(other);
    }

    @JsonbProperty("type")
    @Override
    public final AccountType getType()
    {
        return AccountType.TEAM;
    }

    @JsonbProperty("type")
    public final void setType(final AccountType type)
    {
        if (!(Objects.equals(type, AccountType.TEAM)))
        {
            throw new IllegalArgumentException("AccountType is not TEAM");
        }
    }

    @JsonbProperty("type")
    public final void setType(final String type)
    {
        if (!(Objects.equals(type, AccountType.TEAM.toString())))
        {
            throw new IllegalArgumentException("AccountType is not TEAM");
        }
    }
}