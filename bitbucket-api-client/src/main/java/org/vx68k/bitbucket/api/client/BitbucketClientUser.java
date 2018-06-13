/*
 * BitbucketClientUser.java - class BitbucketClientUser
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client;

import java.util.Map;
import java.util.UUID;
import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * User or team represented by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientUser extends BitbucketClientObject
    implements BitbucketUser
{
    /**
     * Type value for JSON user objects.
     */
    private static final String USER = "user";

    /**
     * Name of the {@code uuid} value in a JSON user object.
     */
    private static final String UUID = "uuid";

    /**
     * Name of the {@code username} value in a JSON user object.
     */
    private static final String USERNAME = "username";

    /**
     * Name of the {@code display_name} value in a JSON user object.
     */
    private static final String DISPLAY_NAME = "display_name";

    /**
     * Name of the {@code links} object in a JSON user object.
     */
    private static final String LINKS = "links";

    /**
     * Constructs this user with a JSON user object.
     *
     * @param userObject JSON object that represents a Bitbucket user
     * @exception IllegalArgumentException if the given JSON object did not
     * represent a user
     */
    public BitbucketClientUser(final JsonObject userObject)
    {
        super(userObject);

        String type = getType();
        if (type == null || !getType().equals(USER)) {
            throw new IllegalArgumentException("JSON object is not user");
        }
    }

    @Override
    public final UUID getUUID()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        JsonObject userObject = getJsonObject();
        return userObject.getString(USERNAME, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDisplayName()
    {
        JsonObject userObject = getJsonObject();
        return userObject.getString(DISPLAY_NAME, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<String, String> getLinks()
    {
        // @todo Implement this method.
        return null;
    }
}
