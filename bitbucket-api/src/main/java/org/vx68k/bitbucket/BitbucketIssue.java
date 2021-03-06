/*
 * BitbucketIssue.java
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
 * Issue on Bitbucket Cloud.
 * An issue belongs to a repository.
 *
 * @author Kaz Nishimura
 * @see BitbucketRepository
 * @since 6.0
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
    BitbucketUserAccount getReporter();

    /**
     * Returns the state of the issue.
     *
     * @return the state of the issue
     */
    String getState();

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
     * Returns the title of the issue.
     *
     * @return the title of the issue
     */
    String getTitle();

    /**
     * Returns the content of the issue.
     *
     * @return the content of the issue
     */
    BitbucketRendered getContent();

    /**
     * Returns the assignee of the issue.
     *
     * @return the assignee of the issue
     */
    BitbucketUserAccount getAssignee();

    /**
     * Returns the component of the issue.
     *
     * @return the component of the issue
     */
    Component getComponent();

    /**
     * Returns the milestone of the issue.
     *
     * @return the milestone of the issue
     */
    Milestone getMilestone();

    /**
     * Returns the version of the issue.
     *
     * @return the version of the issue
     */
    Version getVersion();

    /**
     * Returns the number of votes.
     *
     * @return the number of votes
     */
    int getVotes();

    /**
     * Returns the number of watches.
     *
     * @return the number of watches
     */
    int getWatches();

    /**
     * Returns the instant when the issue was created.
     *
     * @return the instant when the issue was created
     */
    OffsetDateTime getCreated();

    /**
     * Returns the instant when the issue was last updated.
     *
     * @return the instant when the issue was last updated
     */
    OffsetDateTime getUpdated();

    /**
     * Returns the instant when the issue was last edited.
     *
     * @return the instant when the issue was last edited
     */
    OffsetDateTime getEdited();

    /**
     * Issue comment on Bitbucket Cloud.
     */
    interface Comment
    {
        /**
         * Returns the issue to which the comment belongs.
         *
         * @return the issue to which the comment belongs
         */
        BitbucketIssue getIssue();

        /**
         * Returns the identifier of the comment.
         *
         * @return the identifier of the comment
         */
        int getId();

        /**
         * Returns the user who made the comment.
         *
         * @return the user who made the comment
         */
        BitbucketUserAccount getUser();

        /**
         * Returns the content of the comment.
         *
         * @return the content of the comment
         */
        BitbucketRendered getContent();

        /**
         * Returns the time when the comment was created.
         *
         * @return the time when the comment was created
         */
        OffsetDateTime getCreated();

        /**
         * Returns the time when the comment was last updated.
         *
         * @return the time when the comment was last updated
         */
        OffsetDateTime getUpdated();
    }

    /**
     * Issue component resource on Bitbucket Cloud.
     *
     * @author Kaz Nishimura
     */
    interface Component
    {
        /**
         * Returns the name of the component.
         *
         * @return the name of the component
         */
        String getName();
    }

    /**
     * Issue milestone resource on Bitbucket Cloud.
     *
     * @author Kaz Nishimura
     */
    interface Milestone
    {
        /**
         * Returns the name of the milestone.
         *
         * @return the name of the milestone
         */
        String getName();
    }

    /**
     * Issue version resource on Bitbucket Cloud.
     *
     * @author Kaz Nishimura
     */
    interface Version
    {
        /**
         * Returns the name of the version.
         *
         * @return the name of the version
         */
        String getName();
    }
}
