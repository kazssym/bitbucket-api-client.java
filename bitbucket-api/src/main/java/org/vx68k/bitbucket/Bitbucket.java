/*
 * Bitbucket.java
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

package org.vx68k.bitbucket;

import java.util.Collection;
import java.util.List;

/**
 * Root interface for Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public interface Bitbucket
{
    /**
     * Returns an account resource for a user.
     *
     * @param name the name of a user
     * @return an account resource for a user
     */
    BitbucketUserAccount getUserAccount(String name);

    /**
     * Returns an account resource for a team.
     *
     * @param name the name of a team
     * @return an account resource for a team
     */
    BitbucketAccount getTeamAccount(String name);

    /**
     * Finds a repository.
     *
     * @param fullName the full name of a repository
     * @return a repository, or {@code null} if not found
     */
    BitbucketRepository getRepository(String fullName);

    /**
     * Finds a pull request in a repository.
     *
     * @param fullName a full name of a repository
     * @param id a pull request identifier
     * @return the pull request, or {@code null} if not found
     */
    BitbucketPullRequest getPullRequest(String fullName, int id);

    /**
     * Retrieves an issue.
     *
     * @param fullName a full name of a repository
     * @param id an issue identifier
     * @return an issue, or {@code null} if not found
     */
    BitbucketIssue getIssue(String fullName, int id);

    /**
     * Returns a {@link Collection} view of the repositories of an account.
     *
     * @param ownerName the name of an account
     * @return a {@link Collection} view of the repositories of an account
     */
    Collection<BitbucketRepository> repositories(String ownerName);

    /**
     * Retrieves a list of issues that match a filter.
     *
     * @param fullName a full name of a repository
     * @param filter a filter expression, or {@code null}
     * @return a list of issues that match the given filter
     */
    List<BitbucketIssue> getIssues(String fullName, String filter);
}
