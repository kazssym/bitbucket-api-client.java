/*
 * BitbucketRepository.java - interface BitbucketRepository
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
 * Repository on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketRepository extends BitbucketIssueTracker
{
    /**
     * Returns the SCM type of the repository.
     * The return value shall be either {@code "git"} or {@code "hg"}.
     *
     * @return the SCM type
     */
    String getSCM();

    /**
     * Return the owner of this repository.
     *
     * @return the owner
     */
    BitbucketUser getOwner();

    /**
     * Returns the name of this repository.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the UUID of this repository.
     *
     * @return the UUID
     */
    UUID getUUID();

    /**
     * Returns the full name of this repository.
     *
     * @return the full name
     */
    String getFullName();

    /**
     * Returns the main branch of this repository.
     *
     * @return the main branch
     */
    BitbucketBranch getMainBranch();

    /**
     * Returns {@code true} if this repository is private.
     *
     * @return {@code true} if private
     */
    boolean isPrivate();

    /**
     * Returns the creation time of this repository.
     *
     * @return the creation time
     */
    Instant getCreated();

    /**
     * Returns the last update time of this repository.
     *
     * @return the last update time
     */
    Instant getUpdated();

    /**
     * Returns the links of this repository.
     *
     * @return the links
     */
    Map<String, String> getLinks();
}
