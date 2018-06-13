/*
 * BitbucketClientBranch
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
import org.vx68k.bitbucket.api.client.BitbucketClientObject;

/**
 * Branch, named branch, or bookmark on a Bitbucket repository.
 * @author Kaz Nishimura
 */
public class BitbucketClientBranch extends BitbucketClientObject {

    private static final String BRANCH = "branch";
    private static final String NAMED_BRANCH = "named_branch";
    private static final String BOOKMARK = "bookmark";

    private static final Logger logger =
        Logger.getLogger(BitbucketClientBranch.class.getPackage().getName());

    private String name;
    private List<Commit> heads;

    public BitbucketClientBranch(final JsonObject jsonObject) {
        super(jsonObject);
        String entityType = getType();
        if (!(entityType.equals(BRANCH) || entityType.equals(NAMED_BRANCH)
                || entityType.equals(BOOKMARK))) {
            throw new IllegalArgumentException(
                    "Not \"" + BRANCH + "\", \"" + NAMED_BRANCH + "\", or \""
                    + BOOKMARK + "\"");
        }
        logger.log(
                Level.FINE, "Parsing JSON object (\"{0}\"): {1}",
                new Object[] {entityType, jsonObject});
        name = jsonObject.getString(WebhookJsonKeys.NAME);
        heads = parseCommits(jsonObject.getJsonArray(WebhookJsonKeys.HEADS));
    }

    public String getName() {
        return name;
    }

    public List<Commit> getHeads() {
        return heads;
    }

    public void setName(final String value) {
        name = value;
    }

    public void setHeads(final List<Commit> value) {
        heads = value;
    }

    protected static List<Commit> parseCommits(final JsonArray jsonArray) {
        List<Commit> commits = new ArrayList();
        for (JsonValue value : jsonArray) {
            commits.add(new Commit((JsonObject) value));
        }
        return commits;
    }
}
