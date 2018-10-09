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

package org.vx68k.bitbucket.api.client;

import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Client implementation of {@link BitbucketUser}.
 * This class represents a Bitbucket Cloud user account by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientUser extends BitbucketClientAccount implements
    BitbucketUser
{
    /**
     * Type value for users.
     */
    public static final String USER_TYPE = "user";

    /**
     * Name for the {@code is_staff} value in a JSON user object.
     */
    private static final String IS_STAFF = "is_staff";

    /**
     * Name for the {@code account_id} value in a JSON user object.
     */
    private static final String ACCOUNT_ID = "account_id";

    /**
     * Constructs this object with no Bitbucket client.
     *
     * @param object JSON object for a user
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not for a user
     */
    public BitbucketClientUser(final JsonObject object)
    {
        this(object, null);
    }

    /**
     * Constructs this object.
     *
     * @param object JSON object for a user
     * @param client Bitbucket client, or {@code null}
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not for a user
     */
    public BitbucketClientUser(final JsonObject object,
        final BitbucketClient client)
    {
        super(object, client);

        String type = getType();
        if (type == null || !type.equals(USER_TYPE)) {
            throw new IllegalArgumentException("Not user");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isStaff()
    {
        JsonObject object = getJsonObject();
        return object.getBoolean(IS_STAFF, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAccountId()
    {
        JsonObject object = getJsonObject();
        return object.getString(ACCOUNT_ID, null);
    }
}
