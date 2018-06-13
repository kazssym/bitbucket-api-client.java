/*
 * BitbucketPush.java - class BitbucketPush
 * Copyright (C) 2015-2018 Kaz Nishimura
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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.vx68k.bitbucket.api.client.BitbucketClientBranch;
import org.vx68k.bitbucket.api.client.Commit;

/**
 * Push description of a Bitbucket activity.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketPush
{
    /**
     * Name of the {@code changes} array in a JSON push object.
     */
    private static final String CHANGES = "changes";

    /**
     * JSON push object given to the constructor.
     */
    private final JsonObject jsonObject;

    /**
     * Parses a JSON array to a list of changes.
     *
     * @param jsonArray JSON array that represents list of changes
     * @return list of changes
     */
    protected static List<Change> parseChanges(final JsonArray jsonArray)
    {
        if (jsonArray == null) {
            return null;
        }

        List<Change> changes = new ArrayList<Change>();
        for (JsonValue value : jsonArray) {
            changes.add(new Change((JsonObject) value));
        }
        return changes;
    }

    /**
     * Constructs this push description from a JSON event object.
     *
     * @param pushObject JSON push object
     */
    public BitbucketPush(final JsonObject pushObject)
    {
        if (pushObject == null) {
            throw new IllegalArgumentException("JSON object is null");
        }
        jsonObject = pushObject;
    }

    /**
     * Returns the JSON push object given to the constructor.
     *
     * @return the JSON push object
     */
    public final JsonObject getJsonObject()
    {
        return jsonObject;
    }

    /**
     * Returns the list of the changes of this object.
     *
     * @return list of the changes
     */
    public final List<Change> getChanges()
    {
        JsonObject pushObject = getJsonObject();
        return parseChanges(pushObject.getJsonArray(CHANGES));
    }

    /**
     * Change in a push description.
     */
    public static class Change
    {
        /**
         * Name of the {@code created} value in a JSON change object.
         */
        private static final String CREATED = "created";

        /**
         * Name of the {@code closed} value in a JSON change object.
         */
        private static final String CLOSED = "closed";

        /**
         * Name of the {@code forced} value in a JSON change object.
         */
        private static final String FORCED = "forced";

        /**
         * Name of the {@code old} object in a JSON change object.
         */
        private static final String OLD = "old";

        /**
         * Name of the {@code new} object in a JSON change object.
         */
        private static final String NEW = "new";

        /**
         * Name of the {@code commits} array in a JSON change object.
         */
        private static final String COMMITS = "commits";

        /**
         * JSON change object given to the constructor.
         */
        private final JsonObject jsonObject;

        /**
         * Constructs this change with a JSON change object.
         *
         * @param changeObject JSON change object
         */
        public Change(final JsonObject changeObject)
        {
            jsonObject = changeObject;
        }

        /**
         * Returns the JSON change object given to the constructor.
         *
         * @return the JSON change object
         */
        public final JsonObject getJsonObject()
        {
            return jsonObject;
        }

        /**
         * Returns {@code true} if this change created a branch.
         *
         * @return {@code true} if created
         */
        public final boolean isCreated()
        {
            JsonObject changeObject = getJsonObject();
            return changeObject.getBoolean(CREATED, false);
        }

        /**
         * Returns {@code true} if this change closed a branch.
         *
         * @return {@code true} if closed
         */
        public final boolean isClosed()
        {
            JsonObject changeObject = getJsonObject();
            return changeObject.getBoolean(CLOSED, false);
        }

        /**
         * Returns {@code true} if this change was forced.
         *
         * @return {@code true} if forced
         */
        public final boolean isForced()
        {
            JsonObject changeObject = getJsonObject();
            return changeObject.getBoolean(FORCED, false);
        }

        /**
         * Returns the old branch before this change.
         *
         * @return the old branch
         */
        public final BitbucketClientBranch getOld()
        {
            JsonObject changeObject = getJsonObject();
            return new BitbucketClientBranch(
                changeObject.getJsonObject(OLD));
        }

        /**
         * Returns the new branch after this change.
         *
         * @return the new branch
         */
        public final BitbucketClientBranch getNew()
        {
            JsonObject changeObject = getJsonObject();
            return new BitbucketClientBranch(
                changeObject.getJsonObject(NEW));
        }

        /**
         * Returns the commits in this change.
         *
         * @return the commits
         */
        public final List<Commit> getCommits() {
            // TODO: Parse commits.
            return null;
        }
    }
}
