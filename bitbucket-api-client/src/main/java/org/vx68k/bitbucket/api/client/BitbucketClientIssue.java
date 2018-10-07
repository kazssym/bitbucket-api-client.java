/*
 * BitbucketClientIssue.java
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

package org.vx68k.bitbucket.api.client;

import static org.vx68k.bitbucket.api.client.JsonUtilities.toInstant;

import java.time.Instant;
import java.util.function.Function;
import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketAccount;
import org.vx68k.bitbucket.api.BitbucketIssue;
import org.vx68k.bitbucket.api.BitbucketRendered;
import org.vx68k.bitbucket.api.BitbucketRepository;

/**
 *
 * @author Kaz Nishimura
 */
public class BitbucketClientIssue extends BitbucketClientObject implements
    BitbucketIssue
{
    /**
     * Type value for issues.
     */
    private static final String ISSUE = "issue";

    /**
     * Name for the {@code repository} value in a JSON object.
     */
    private static final String REPOSITORY = "repository";

    /**
     * Name for the {@code id} value in a JSON object.
     */
    private static final String ID = "id";

    /**
     * Name for the {@code title} value in a JSON object.
     */
    private static final String TITLE = "title";

    /**
     * Name for the {@code content} value in a JSON object.
     */
    private static final String CONTENT = "content";

    /**
     * Name for the {@code reporter} value in a JSON object.
     */
    private static final String REPORTER = "reporter";

    /**
     * Name for the {@code assignee} value in a JSON object.
     */
    private static final String ASSIGNEE = "assignee";

    /**
     * Name for the {@code kind} value in a JSON object.
     */
    private static final String KIND = "kind";

    /**
     * Name for the {@code priority} value in a JSON object.
     */
    private static final String PRIORITY = "priority";

    /**
     * Name for the {@code state} value in a JSON object.
     */
    private static final String STATE = "state";

    /**
     * Name for the {@code created_on} value in a JSON object.
     */
    private static final String CREATED_ON = "created_on";

    /**
     * Name for the {@code updated_on} value in a JSON object.
     */
    private static final String UPDATED_ON = "updated_on";

    /**
     * Name for the {@code edited_on} value in a JSON object.
     */
    private static final String EDITED_ON = "edited_on";

    /**
     * Initializes the object from a JSON object.
     * This constructor sets the Bitbucket API client to {@code null}.
     *
     * @param jsonObject a JSON object
     */
    public BitbucketClientIssue(final JsonObject jsonObject)
    {
        this(jsonObject, null);
    }

    /**
     * Initializes the object from a JSON object.
     *
     * @param jsonObject a JSON object
     * @param bitbucketClient a Bitbucket API client
     */
    public BitbucketClientIssue(
        final JsonObject jsonObject, final BitbucketClient bitbucketClient)
    {
        super(jsonObject, bitbucketClient);

        String type = getType();
        if (!ISSUE.equals(type)) {
            throw new IllegalArgumentException("Object is not of an issue");
        }
    }

    /**
     * Returns a function to create an issue form a JSON object.
     *
     * @return a function to create an issue from a JSON object
     */
    public static Function<JsonObject, BitbucketClientIssue> create()
    {
        return create(null);
    }

    /**
     * Returns a function to create an issue form a JSON object.
     *
     * @param bitbucketClient a Bitbucket API client
     * @return a function to create an issue from a JSON object
     */
    public static Function<JsonObject, BitbucketClientIssue> create(
        final BitbucketClient bitbucketClient)
    {
        return (object) ->
            new BitbucketClientIssue(object, bitbucketClient);
    }

    @Override
    public final BitbucketRepository getRepository()
    {
        JsonObject object = getJsonObject();
        JsonObject repository = object.getJsonObject(REPOSITORY);

        BitbucketRepository value = null;
        if (repository != null) {
            value = new BitbucketClientRepository(
                repository, getBitbucketClient());
        }
        return value;
    }

    @Override
    public final int getId()
    {
        JsonObject object = getJsonObject();
        return object.getInt(ID, 0);
    }

    @Override
    public final String getTitle()
    {
        JsonObject object = getJsonObject();
        return object.getString(TITLE, null);
    }

    @Override
    public final BitbucketRendered getContent()
    {
        // @todoo Implement this method.
        return null;
    }

    @Override
    public final BitbucketAccount getReporter()
    {
        JsonObject object = getJsonObject();
        JsonObject reporter = object.getJsonObject(REPORTER);

        BitbucketAccount value = null;
        if (reporter != null) {
            value = new BitbucketClientAccount(reporter, getBitbucketClient());
        }
        return value;
    }

    @Override
    public final BitbucketAccount getAssignee()
    {
        JsonObject object = getJsonObject();
        JsonObject assignee = object.getJsonObject(ASSIGNEE);

        BitbucketAccount value = null;
        if (assignee != null) {
            value = new BitbucketClientAccount(assignee, getBitbucketClient());
        }
        return value;
    }

    @Override
    public final String getKind()
    {
        JsonObject object = getJsonObject();
        return object.getString(KIND, null);
    }

    @Override
    public final String getPriority()
    {
        JsonObject object = getJsonObject();
        return object.getString(PRIORITY, null);
    }

    @Override
    public final String getState()
    {
        JsonObject object = getJsonObject();
        return object.getString(STATE, null);
    }

    @Override
    public final Component getComponent()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Milestone getMilestone()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Version getVersion()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final int getVotes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final int getWatches()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Instant getCreated()
    {
        JsonObject object = getJsonObject();
        // This may be a JSON null.
        Instant value = null;
        if (object.containsKey(CREATED_ON) && !object.isNull(CREATED_ON)) {
            value = toInstant(object.getJsonString(CREATED_ON));
        }
        return value;
    }

    @Override
    public final Instant getUpdated()
    {
        JsonObject object = getJsonObject();
        // This may be a JSON null.
        Instant value = null;
        if (object.containsKey(UPDATED_ON) && !object.isNull(UPDATED_ON)) {
            value = toInstant(object.getJsonString(UPDATED_ON));
        }
        return value;
    }

    @Override
    public final Instant getEdited()
    {
        JsonObject object = getJsonObject();
        // This may be a JSON null.
        Instant value = null;
        if (object.containsKey(EDITED_ON) && !object.isNull(EDITED_ON)) {
            value = toInstant(object.getJsonString(EDITED_ON));
        }
        return value;
    }
}
