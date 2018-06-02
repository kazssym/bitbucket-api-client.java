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
    public RepositoryPush(JsonObject jsonObject) {
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
     * @param changes list of the change
     */
    public void setChanges(List<Change> changes) {
        this.changes = changes;
    }

    /**
     * Parses a JSON array to a list of changes.
     * @param jsonArray JSON array that represents list of changes
     * @return list of changes
     */
    protected List<Change> parseChanges(JsonArray jsonArray) {
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

        public Change(JsonObject jsonObject) {
            logger.log(
                    Level.INFO, "Parsing JSON object (change): {0}",
                    jsonObject);
            created = jsonObject.getBoolean(WebhookJsonKeys.CREATED);
            closed = jsonObject.getBoolean(WebhookJsonKeys.CLOSED);
            forced = jsonObject.getBoolean(WebhookJsonKeys.FORCED);
            oldState = new WebhookBranch(jsonObject.getJsonObject(
                    WebhookJsonKeys.OLD));
            newState = new WebhookBranch(jsonObject.getJsonObject(
                    WebhookJsonKeys.NEW));
            // TODO: Parse commits.

            this.jsonObject = jsonObject;
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

        public void setCreated(boolean created) {
            this.created = created;
        }

        public void setClosed(boolean closed) {
            this.closed = closed;
        }

        public void setForced(boolean forced) {
            this.forced = forced;
        }

        public void setOldState(WebhookBranch oldState) {
            this.oldState = oldState;
        }

        public void setNewState(WebhookBranch newState) {
            this.newState = newState;
        }

        public void setCommits(List<Commit> commits) {
            this.commits = commits;
        }
    }
}
