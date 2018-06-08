/*
 * RepositoryPush - represents a push to a Bitbucket repository
 * Copyright (C) 2015 Nishimura Software Studio
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.vx68k.bitbucket.webhook;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.vx68k.bitbucket.api.client.Commit;

/**
 * Represents a push to a Bitbucket repository.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class RepositoryPush extends Activity {

    private static final Logger logger =
            Logger.getLogger(RepositoryPush.class.getPackage().getName());

    private List<Change> changes;

    /**
     * Constructs this object from a JSON object.
     *
     * @param jsonObject JSON object
     */
    public RepositoryPush(final JsonObject jsonObject) {
        super(jsonObject);

        JsonObject push = jsonObject.getJsonObject(WebhookJsonKeys.PUSH);
        changes = parseChanges(push.getJsonArray(WebhookJsonKeys.CHANGES));
    }

    /**
     * Returns the list of the changes of this object.
     * @return list of the changes
     */
    public List<Change> getChanges() {
        return changes;
    }

    /**
     * Sets the list of the changes of this object.
     * @param value list of the change
     */
    public void setChanges(final List<Change> value) {
        changes = value;
    }

    /**
     * Parses a JSON array to a list of changes.
     * @param jsonArray JSON array that represents list of changes
     * @return list of changes
     */
    protected List<Change> parseChanges(final JsonArray jsonArray) {
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
     * Change of a Bitbucket repository.
     * @since 4.0
     */
    public static class Change {

        private boolean created;
        private boolean closed;
        private boolean forced;
        private WebhookBranch oldState;
        private WebhookBranch newState;
        private List<Commit> commits;

        // TODO: Remove this field.
        private final JsonObject jsonObject;

        public Change(final JsonObject initJsonObject) {
            logger.log(
                    Level.INFO, "Parsing JSON object (change): {0}",
                    initJsonObject);
            created = initJsonObject.getBoolean(WebhookJsonKeys.CREATED);
            closed = initJsonObject.getBoolean(WebhookJsonKeys.CLOSED);
            forced = initJsonObject.getBoolean(WebhookJsonKeys.FORCED);
            oldState = new WebhookBranch(initJsonObject.getJsonObject(
                    WebhookJsonKeys.OLD));
            newState = new WebhookBranch(initJsonObject.getJsonObject(
                    WebhookJsonKeys.NEW));
            // TODO: Parse commits.

            jsonObject = initJsonObject;
        }

        public boolean isCreated() {
            return created;
        }

        public boolean isClosed() {
            return closed;
        }

        public boolean isForced() {
            return forced;
        }

        public WebhookBranch getOldState() {
            return oldState;
        }

        public WebhookBranch getNewState() {
            return newState;
        }

        public List<Commit> getCommits() {
            return commits;
        }

        public JsonObject getJsonObject() {
            return jsonObject;
        }

        public void setCreated(final boolean value) {
            created = value;
        }

        public void setClosed(final boolean value) {
            closed = value;
        }

        public void setForced(final boolean value) {
            forced = value;
        }

        public void setOldState(final WebhookBranch value) {
            oldState = value;
        }

        public void setNewState(final WebhookBranch value) {
            newState = value;
        }

        public void setCommits(final List<Commit> value) {
            commits = value;
        }
    }
}
