/*
 * BitbucketIssueTracker.java - interface BitbucketIssueTracker
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

import java.util.Collection;

/**
 * Issue tracker interface for a Bitbucket Cloud repository.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketIssueTracker
{
    /**
     * Returns the issue specified by an identifier.
     *
     * @param id identifier of the issue
     * @return issue if found, {@code null} otherwise
     */
    BitbucketIssue getIssue(int id);

    /**
     * Returns a collection view of the issues.
     * The order of the issues are unspecified.
     *
     * @return {@link Collection} object
     */
    Collection<BitbucketIssue> issues();
}
