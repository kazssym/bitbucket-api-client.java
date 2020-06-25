/*
 * Bitbucket.java
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

package org.vx68k.bitbucket;

import java.util.Collection;

/**
 * Abstraction of the Bitbucket API.
 *
 * @author Kaz Nishimura
 * @see BitbucketAccount
 * @see BitbucketRepository
 * @since 5.0
 */
public interface Bitbucket
{
    /**
     * Returns an account resource for a user.
     *
     * @param name the name of a user
     * @return an account resource for a user
     */
    BitbucketAccount getUser(String name);

    /**
     * Returns an account resource for a team.
     *
     * @param name the name of a team
     * @return an account resource for a team
     */
    BitbucketAccount getTeam(String name);

    /**
     * Returns a repository resource for a repository.
     *
     * @param ownerName the owner name of a repository
     * @param name the name of a repository
     * @return a repository resource for a repository
     */
    BitbucketRepository getRepository(String ownerName, String name);

    /**
     * Returns a {@link Collection} view of the repositories of an account.
     *
     * @param ownerName the name of an account
     * @return a {@link Collection} view of the repositories of an account
     */
    Collection<BitbucketRepository> repositories(String ownerName);
}
