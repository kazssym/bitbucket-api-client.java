/*
 * BitbucketClientUser.java
 * Copyright (C) 2018 Kaz Nishimura
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

import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbProperty;

import org.vx68k.bitbucket.BitbucketUser;

/**
 * Client implementation of {@link BitbucketUser}.
 *
 * @author Kaz Nishimura
 * @since 6.0.0
 */
public class BitbucketClientUser extends BitbucketClientAccount
    implements BitbucketUser // TODO: Make this class package-scoped.
{
    /**
     * Name for the {@code is_staff} value in a JSON user object.
     */
    private static final String JSON_STAFF = "is_staff";

    /**
     * Name for the {@code account_id} value in a JSON user object.
     */
    private static final String JSON_ACCOUNT_ID = "account_id";

    @JsonbProperty(JSON_STAFF)
    private boolean staff = false;

    @JsonbProperty(JSON_ACCOUNT_ID)
    private String accountId;

    /**
     * Constructs a user account.
     */
    public BitbucketClientUser()
    {
        // Nothing to do.
    }

    /**
     * Constructs a user account copying another.
     *
     * @param other another user account
     */
    public BitbucketClientUser(final BitbucketClientUser other)
    {
        super(other);

        this.staff = other.staff;
        this.accountId = other.accountId;
    }

    /**
     * Constructs a user account from a JSON object.
     *
     * @param json a JSON object
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not of a user account
     */
    public BitbucketClientUser(final JsonObject json)
    {
        super(json);

        String type = json.getString("type", null);
        if (!USER.equals(type)) {
            throw new IllegalArgumentException("JSON object is not of a user account");
        }

        this.staff = json.getBoolean(JSON_STAFF, false);
        this.accountId = json.getString(JSON_ACCOUNT_ID, null);
    }

    public final String getType()
    {
        return USER;
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
