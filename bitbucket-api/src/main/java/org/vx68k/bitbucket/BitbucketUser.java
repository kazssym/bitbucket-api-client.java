/*
 * BitbucketUser.java - interface BitbucketUser
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

package org.vx68k.bitbucket;

/**
 * User on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketUser extends BitbucketAccount
{
    /**
     * Returns {@code true} if the user is staff.
     *
     * @return {@code true} if staff; {@code false} otherwise
     */
    boolean isStaff();

    /**
     * Returns the Atlassian account identifier.
     *
     * @return the Atlassian account identifier
     */
    String getAccountId();
}
