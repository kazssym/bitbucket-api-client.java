/*
 * WebhookPush.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

package org.vx68k.bitbucket.webhook;

import java.util.Arrays;
import javax.json.bind.annotation.JsonbTypeAdapter;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.client.adapter.BitbucketBranchAdapter;

/**
 * Class of push activities.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class WebhookPush
{
    private Change[] changes;

    /**
     * Constructs a push activity.
     */
    public WebhookPush()
    {
        // Nothing to do.
    }

    public WebhookPush(final WebhookPush other)
    {
        setChanges(other.changes);
    }

    /**
     * Returns the changes included in this push.
     *
     * @return the changes
     */
    public final Change[] getChanges()
    {
        return Arrays.copyOf(changes, changes.length);
    }

    public final void setChanges(Change[] changes)
    {
        if (changes != null) {
            changes = Arrays.copyOf(changes, changes.length);
        }
        this.changes = changes;
    }

    /**
     * Class of repository changes in a push.
     */
    public static class Change
    {
        private boolean created;

        private boolean closed;

        private boolean forced;

        private boolean truncated;

        private BitbucketBranch old;

        private BitbucketBranch new1;

        private BitbucketCommit[] commits;

        /**
         * Constructs a change.
         */
        public Change()
        {
            // Nothing to do.
        }

        public Change(final Change other)
        {
            this.created = other.created;
            this.closed = other.closed;
            this.forced = other.forced;
            this.truncated = other.truncated;

            setOld(other.old);
            setNew(other.new1);
            setCommits(other.commits);
        }

        /**
         * Returns {@code true} if this change created a branch.
         *
         * @return {@code true} if created
         */
        public final boolean isCreated()
        {
            return created;
        }

        public final void setCreated(final boolean created)
        {
            this.created = created;
        }

        /**
         * Returns {@code true} if this change closed a branch.
         *
         * @return {@code true} if closed
         */
        public final boolean isClosed()
        {
            return closed;
        }

        public final void setClosed(final boolean closed)
        {
            this.closed = closed;
        }

        /**
         * Returns {@code true} if this change was forced.
         *
         * @return {@code true} if forced
         */
        public final boolean isForced()
        {
            return forced;
        }

        public final void setForced(final boolean forced)
        {
            this.forced = forced;
        }

        /**
         * Returns {@code true} if this change was truncated.
         *
         * @return {@code true} if truncated
         */
        public final boolean isTruncated()
        {
            return truncated;
        }

        public final void setTruncated(final boolean truncated)
        {
            this.truncated = truncated;
        }

        /**
         * Returns the old branch before this change.
         *
         * @return the old branch
         */
        @JsonbTypeAdapter(BitbucketBranchAdapter.class)
        public final BitbucketBranch getOld()
        {
            return old;
        }

        public final void setOld(BitbucketBranch old)
        {
            this.old = old;
        }

        /**
         * Returns the new branch after this change.
         *
         * @return the new branch
         */
        @JsonbTypeAdapter(BitbucketBranchAdapter.class)
        public final BitbucketBranch getNew()
        {
            return new1;
        }

        public final void setNew(BitbucketBranch new1)
        {
            this.new1 = new1;
        }

        /**
         * Returns the commits included in this change.
         *
         * @return the commits
         */
        public final BitbucketCommit[] getCommits()
        {
            return Arrays.copyOf(commits, commits.length);
        }

        public final void setCommits(BitbucketCommit[] commits)
        {
            if (commits != null) {
                commits = Arrays.copyOf(commits, commits.length);
            }
            this.commits = commits;
        }
    }
}
