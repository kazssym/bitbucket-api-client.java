/*
 * ClientUserAccount.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketUser;
import org.vx68k.bitbucket.client.internal.ClientAccount;

/**
 * Client implementation of {@link BitbucketUser}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientUserAccount extends ClientAccount implements BitbucketUser
{
    @JsonbProperty("is_staff")
    private boolean staff = false;

    @JsonbProperty("account_id")
    private String accountId;

    /**
     * Constructs a user account.
     */
    public ClientUserAccount()
    {
        // Nothing to do.
    }

    /**
     * Constructs a user account copying another.
     *
     * @param other another user account
     */
    public ClientUserAccount(final ClientUserAccount other)
    {
        super(other);

        this.staff = other.staff;
        this.accountId = other.accountId;
    }

    @JsonbProperty("type")
    @Override
    public final AccountType getType()
    {
        return AccountType.USER;
    }

    @JsonbProperty("type")
    public final void setType(final AccountType type)
    {
        if (type != null && !(type.equals(AccountType.USER))) {
            throw new IllegalArgumentException("AccountType is not USER");
        }
    }

    @JsonbProperty("type")
    public final void setType(final String type)
    {
        if (type != null && !(type.equals(AccountType.USER.toString()))) {
            throw new IllegalArgumentException("AccountType is not USER");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isStaff()
    {
        return staff;
    }

    /**
     * Set the staff flag of the user account.
     *
     * @param staff a Boolean value for the staff flag
     */
    public final void setStaff(final boolean staff)
    {
        this.staff = staff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAccountId()
    {
        return accountId;
    }

    public final void setAccountId(final String accountId)
    {
        this.accountId = accountId;
    }
}
