/*
 * BitbucketCommit.java
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

import java.time.OffsetDateTime;

/**
 * Class of commits in a repository.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public interface BitbucketCommit
{
    /**
     * Returns the hash of the commit.
     * This is the primary identifier.
     *
     * @return the hash of the commit
     */
    String getHash();

    /**
     *
     * @return the date
     */
    OffsetDateTime getDate();

    /**
     *
     * @return the message
     */
    String getMessage();

    /**
     *
     * @return the repository
     */
    BitbucketRepository getRepository();

    /**
     *
     * @return the list of the parents
     */
    BitbucketCommit[] getParents();

    /**
     *
     * @return the summary
     */
    BitbucketRendered getSummary();

    /**
     * Class of commit refs in a repository.
     *
     * @author Kaz Nishimura
     * @since 6.0
     */
    static interface Ref
    {
        /**
         * Returns the name of the commit ref.
         *
         * @return the name of the commit ref
         */
        String getName();

        /**
         * Returns the target of the commit ref.
         *
         * @return the target of the commit ref
         */
        BitbucketCommit getTarget();
    }
}
