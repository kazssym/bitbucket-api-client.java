/*
 * BitbucketIssue.java - interface BitbucketIssue
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

/**
 * Issue in a Bitbucket issue tracker.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public interface BitbucketIssue
{
    /**
     * Returns the repository of the issue.
     *
     * @return the repository of the issue
     */
    BitbucketRepository getRepository();

    /**
     * Returns the identifier of the issue.
     *
     * @return the identifier of the issue
     */
    int getId();

    /**
     * Returns the reporter of the issue.
     *
     * @return the reporter of the issue
     */
    BitbucketAccount getReporter();

    /**
     * Returns the title of the issue.
     *
     * @return the title of the issue
     */
    String getTitle();

    /**
     * Returns the state of the issue.
     *
     * @return the state of the issue
     */
    String getState();

    /**
     * Returns the assignee of the issue.
     *
     * @return the assignee of the issue
     */
    BitbucketAccount getAssignee();

    /**
     * Returns the kind of the issue.
     *
     * @return the kind of the issue
     */
    String getKind();

    /**
     * Returns the priority of the issue.
     *
     * @return the priority of the issue
     */
    String getPriority();

    /**
     * Returns the instant when the issue was created.
     *
     * @return the instant when the issue was created
     */
    Instant getCreated();

    /**
     * Returns the instant when the issue was last updated.
     *
     * @return the instant when the issue was last updated
     */
    Instant getUpdated();

    /**
     * Returns the instant when the issue was last edited.
     *
     * @return the instant when the issue was last edited
     */
    Instant getEdited();
}
