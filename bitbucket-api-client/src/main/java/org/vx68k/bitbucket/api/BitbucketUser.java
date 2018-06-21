/*
 * BitbucketUser.java - interface BitbucketUser
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

package org.vx68k.bitbucket.api;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * User or team on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketUser
{
    /**
     * Type value for users.
     */
    String USER = "user";

    /**
     * Type value for teams.
     */
    String TEAM = "team";

    /**
     * Returns the type of this object.
     *
     * @return the type
     */
    String getType();

    /**
     * Returns the UUID of this user.
     *
     * @return the UUID
     */
    UUID getUUID();

    /**
     * Returns the name of this user.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the display name of this user.
     *
     * @return the display name
     */
    String getDisplayName();

    /**
     * Returns the website of this user.
     *
     * @return the website
     */
    String getWebsite();

    /**
     * Returns the location of this user.
     *
     * @return the location
     */
    String getLocation();

    /**
     * Returns the instant when this user was created.
     *
     * @return the instant
     */
    Instant getCreated();

    /**
     * Returns the links of this user.
     *
     * @return the links
     */
    Map<String, String> getLinks();
}
