/*
 * BitbucketAccount.java - interface BitbucketAccount
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
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Account on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketAccount
{
    /**
     * Returns the name of the account.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the UUID of the account.
     *
     * @return the UUID
     */
    UUID getUUID();

    /**
     * Returns the display name of the account.
     *
     * @return the display name
     */
    String getDisplayName();

    /**
     * Returns the website of the account.
     *
     * @return the website
     */
    String getWebsite();

    /**
     * Returns the location of the account.
     *
     * @return the location
     */
    String getLocation();

    /**
     * Returns the instant when the account was created.
     *
     * @return the instant
     */
    Instant getCreated();

    /**
     * Returns the links of the account.
     *
     * @return the links
     */
    Map<String, String> getLinks();

    /**
     * Returns a repository which is owned by the account.
     *
     * @param name repository name
     * @return {@link BitbucketRepository} object for the repository if one was
     * found; {@code null} otherwise
     */
    BitbucketRepository getRepository(String name);

    /**
     * Returns a {@link Collection} view of the repositories which are owned by
     * the account.
     *
     * @return {@link Collection} view of the repositories
     */
    Collection<BitbucketRepository> repositories();
}
