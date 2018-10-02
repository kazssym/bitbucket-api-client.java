/*
 * BitbucketAccount.java
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

package org.vx68k.bitbucket.api;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

/**
 * Account resource on Bitbucket Cloud.
 * A Bitbucket Cloud account is either a user or a team.
 * This interface can represent both types of accounts, but the attributes
 * specific to user accounts are not accessible with this interface but with
 * {@link BitbucketUser}.
 *
 * @author Kaz Nishimura
 * @see BitbucketUser
 * @see BitbucketRepository
 * @since 5.0
 */
public interface BitbucketAccount
{
    /**
     * Type of user accounts.
     */
    String USER = "user";

    /**
     * Type of team accounts.
     */
    String TEAM = "team";

    /**
     * Return the type of the account.
     *
     * @return the type of the account
     */
    String getType();

    /**
     * Returns the name of the account.
     * This property identifies the account.
     *
     * @return the name of the account
     */
    String getName();

    /**
     * Returns the UUID of the account.
     *
     * @return the UUID of the account
     */
    UUID getUUID();

    /**
     * Returns the display name of the account.
     *
     * @return the display name of the account
     */
    String getDisplayName();

    /**
     * Returns the website URI of the account.
     *
     * @return the website URI of the account
     */
    String getWebsite();

    /**
     * Returns the location of the account.
     *
     * @return the location of the account
     */
    String getLocation();

    /**
     * Returns the date and time when the account was created.
     *
     * @return the date and time when the account was created
     */
    Instant getCreated();

    /**
     * Returns a repository owned by the account.
     *
     * @param name the name of a repository
     * @return a repository owned by the account if one was found; {@code null}
     * otherwise
     */
    BitbucketRepository getRepository(String name);

    /**
     * Returns a {@link Collection} view of the repositories owned by the
     * account.
     *
     * @return a {@link Collection} view of the repositories owned by the
     * account
     */
    Collection<BitbucketRepository> repositories();
}
