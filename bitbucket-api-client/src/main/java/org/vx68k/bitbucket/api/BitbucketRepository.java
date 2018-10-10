/*
 * BitbucketRepository.java
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
import java.util.UUID;

/**
 * Repository on Bitbucket Cloud.
 * A Bitbucket Cloud repository is owned by an account.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketRepository
{
    /**
     * SCM type value for Git repositories.
     */
    String GIT = "git";

    /**
     * SCM type value for Mercurial repositories.
     */
    String HG = "hg";

    /**
     * Returns the SCM type of the repository.
     * The return value shall be either {@code #GIT} or {@code #HG}.
     *
     * @return the SCM type of the repository
     */
    String getSCM();

    /**
     * Returns the owner of the repository.
     *
     * @return the owner of the repository
     */
    BitbucketAccount getOwner();

    /**
     * Returns the name of the repository.
     *
     * @return the name of the repository
     */
    String getName();

    /**
     * Returns the full name of the repository.
     *
     * @return the full name of the repository
     */
    String getFullName();

    /**
     * Returns the UUID of the repository.
     *
     * @return the UUID of the repository
     */
    UUID getUUID();

    /**
     * Returns the description of the repository.
     *
     * @return the description of the repository
     */
    String getDescription();

    /**
     * Returns {@code true} if and only if the repository is private.
     *
     * @return {@code true} if and only if the repository is private
     */
    boolean isPrivate();

    /**
     * Returns the main branch of the repository.
     *
     * @return the main branch of the repository
     */
    BitbucketBranch getMainBranch();

    /**
     * Returns {@code true} if and only if the repository has a issue tracker.
     *
     * @return {@code true} if and only if the repository has a issue tracker
     */
    boolean hasIssueTracker();

    /**
     * Returns {@code true} if and only if the repository has a wiki.
     *
     * @return {@code true} if and only if the repository has a wiki
     */
    boolean hasWiki();

    /**
     * Returns the size of the repository.
     *
     * @return the size of the repository
     */
    long getSize();

    /**
     * Returns the time when the repository was created.
     *
     * @return the time when the repository was created
     */
    Instant getCreated();

    /**
     * Returns the time when the repository was last updated.
     *
     * @return the time when the repository was last updated
     */
    Instant getUpdated();
}
