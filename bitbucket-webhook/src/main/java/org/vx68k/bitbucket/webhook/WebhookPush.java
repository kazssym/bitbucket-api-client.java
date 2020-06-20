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

import java.util.List;
import java.util.stream.Collectors;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.client.BitbucketClientObject;

/**
 * Push description of a Bitbucket activity.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class WebhookPush extends BitbucketClientObject
{
    /**
     * Name for the {@code changes} array in a JSON push object.
     */
    private static final String CHANGES = "changes";

    /**
     * Constructs this push description from a JSON push object.
     *
     * @param pushObject JSON push object
     */
    public WebhookPush(final JsonObject pushObject)
    {
        super(pushObject);
    }

    /**
     * Returns the changes included in this push.
     *
     * @return the changes
     */
    public final List<Change> getChanges()
    {
        JsonObject object = getJsonObject();
        JsonArray array = object.getJsonArray(CHANGES);
        List<Change> changes = null;
        if (array != null) {
            changes = array.stream()
                .map((x) -> new Change((JsonObject) x))
                .collect(Collectors.toList());
        }
        return changes;
    }

    /**
     * Change in a push description.
     */
    public static class Change extends BitbucketClientObject
    {
        /**
         * Name for the {@code created} value in a JSON change object.
         */
        private static final String CREATED = "created";

        /**
         * Name for the {@code closed} value in a JSON change object.
         */
        private static final String CLOSED = "closed";

        /**
         * Name for the {@code forced} value in a JSON change object.
         */
        private static final String FORCED = "forced";

        /**
         * Name for the {@code truncated} value in a JSON change object.
         */
        private static final String TRUNCATED = "truncated";

        /**
         * Name for the {@code old} object in a JSON change object.
         */
        private static final String OLD = "old";

        /**
         * Name for the {@code new} object in a JSON change object.
         */
        private static final String NEW = "new";

        /**
         * Name for the {@code commits} array in a JSON change object.
         */
        private static final String COMMITS = "commits";

        /**
         * Constructs this change with a JSON change object.
         *
         * @param object JSON change object
         */
        public Change(final JsonObject object)
        {
            super(object);
        }

        /**
         * Returns {@code true} if this change created a branch.
         *
         * @return {@code true} if created
         */
        public final boolean isCreated()
        {
            JsonObject object = getJsonObject();
            return object.getBoolean(CREATED, false);
        }

        /**
         * Returns {@code true} if this change closed a branch.
         *
         * @return {@code true} if closed
         */
        public final boolean isClosed()
        {
            JsonObject object = getJsonObject();
            return object.getBoolean(CLOSED, false);
        }

        /**
         * Returns {@code true} if this change was forced.
         *
         * @return {@code true} if forced
         */
        public final boolean isForced()
        {
            JsonObject object = getJsonObject();
            return object.getBoolean(FORCED, false);
        }

        /**
         * Returns {@code true} if this change was truncated.
         *
         * @return {@code true} if truncated
         */
        public final boolean isTruncated()
        {
            JsonObject object = getJsonObject();
            return object.getBoolean(TRUNCATED, false);
        }

        /**
         * Returns the old branch before this change.
         *
         * @return the old branch
         */
        public final BitbucketBranch getOld()
        {
            JsonObject object = getJsonObject();
            BitbucketBranch value = null;
            if (object.containsKey(OLD) && !object.isNull(OLD)) {
                // value = new ClientBranch(
                //     object.getJsonObject(OLD));
            }
            return value;
        }

        /**
         * Returns the new branch after this change.
         *
         * @return the new branch
         */
        public final BitbucketBranch getNew()
        {
            JsonObject object = getJsonObject();
            BitbucketBranch value = null;
            if (object.containsKey(NEW) && !object.isNull(NEW)) {
                // value = new ClientBranch(
                //     object.getJsonObject(NEW));
            }
            return value;
        }

        /**
         * Returns the commits included in this change.
         *
         * @return the commits
         */
        public final List<BitbucketCommit> getCommits()
        {
            JsonObject object = getJsonObject();
            JsonArray array = object.getJsonArray(COMMITS);
            List<BitbucketCommit> commits = null;
            if (array != null) {
                // commits = array.stream()
                //     .map((x) -> new ClientCommit((JsonObject) x))
                //     .collect(Collectors.toList());
            }
            return commits;
        }
    }
}
