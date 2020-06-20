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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketCommit;

/**
 * Class of push activities.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class WebhookPush
{
    List<Change> changes;

    /**
     * Constructs a push activity.
     */
    public WebhookPush()
    {
        // Nothing to do.
    }

    public WebhookPush(final WebhookPush other)
    {
        List<Change> otherChanges = other.getChanges();
        if (otherChanges != null) {
            otherChanges = otherChanges.stream().map(Change::new)
                .collect(Collectors.toCollection(ArrayList::new));
        }
        this.changes = otherChanges;
    }

    /**
     * Returns the changes included in this push.
     *
     * @return the changes
     */
    public final List<Change> getChanges()
    {
        return changes;
    }

    public final void setChanges(List<Change> changes)
    {
        if (changes != null) {
            changes = changes.stream().map(Change::new)
                .collect(Collectors.toCollection(ArrayList::new));
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

        private List<BitbucketCommit> commits;

        /**
         * Constructs this change with a JSON change object.
         *
         * @param object JSON change object
         */
        public Change()
        {
            // Nothing to do.
        }

        public Change(final Change other)
        {
            this.created = other.isCreated();
            this.closed = other.isClosed();
            this.forced = other.isForced();
            this.truncated = other.isTruncated();

            BitbucketBranch otherOld = other.getOld();
            if (otherOld != null) {
                otherOld = otherOld; // TODO: Make a copy.
            }
            this.old = otherOld;

            BitbucketBranch otherNew = other.getNew();
            if (otherNew != null) {
                otherNew = otherNew; // TODO: Make a copy.
            }
            this.new1 = otherNew;
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
        public final BitbucketBranch getOld()
        {
            return old;
        }

        public final void setOld(BitbucketBranch old)
        {
            if (old != null) {
                old = old; // TODO: Make a copy.
            }
            this.old = old;
        }

        /**
         * Returns the new branch after this change.
         *
         * @return the new branch
         */
        @JsonbProperty("new")
        public final BitbucketBranch getNew()
        {
            return new1;
        }

        @JsonbProperty("new")
        public final void setNew(BitbucketBranch new1)
        {
            if (new1 != null) {
                new1 = new1; // TODO: Make a copy.
            }
            this.new1 = new1;
        }

        /**
         * Returns the commits included in this change.
         *
         * @return the commits
         */
        public final List<BitbucketCommit> getCommits()
        {
            return commits;
        }

        public final void setCommits(List<BitbucketCommit> commits)
        {
            if (commits != null) {
                commits = commits.stream() // .map(BitbucketClient::copyCommit)
                    .collect(Collectors.toCollection(ArrayList::new)); // TODO: Deep copy?
            }
            this.commits = commits;
        }
    }
}
