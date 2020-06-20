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

package org.vx68k.bitbucket.client;

import static org.vx68k.bitbucket.client.JsonUtilities.toInstant;
import java.time.Instant;
import java.util.function.Function;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import org.vx68k.bitbucket.BitbucketIssue;
import org.vx68k.bitbucket.BitbucketRendered;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUserAccount;
import org.vx68k.bitbucket.client.internal.ClientUserAccount;

/**
 * Client implementation of {@link BitbucketIssue}.
 * This class represents a issue by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientIssue extends BitbucketClientObject implements
    BitbucketIssue
{
    /**
     * Type value for issues.
     */
    private static final String ISSUE = "issue";

    /**
     * Name of the {@code assignee} value in a JSON issue object.
     */
    private static final String ASSIGNEE = "assignee";

    /**
     * Name of the {@code component} value in a JSON issue object.
     */
    private static final String COMPONENT = "component";

    /**
     * Name of the {@code content} value in a JSON issue object.
     */
    private static final String CONTENT = "content";

    /**
     * Name of the {@code created_on} value in a JSON issue object.
     */
    private static final String CREATED_ON = "created_on";

    /**
     * Name of the {@code edited_on} value in a JSON issue object.
     */
    private static final String EDITED_ON = "edited_on";

    /**
     * Name of the {@code id} value in a JSON issue object.
     */
    private static final String ID = "id";

    /**
     * Name of the {@code kind} value in a JSON issue object.
     */
    private static final String KIND = "kind";

    /**
     * Name of the {@code milestone} value in a JSON issue object.
     */
    private static final String MILESTONE = "milestone";

    /**
     * Name of the {@code priority} value in a JSON issue object.
     */
    private static final String PRIORITY = "priority";

    /**
     * Name of the {@code reporter} value in a JSON issue object.
     */
    private static final String REPORTER = "reporter";

    /**
     * Name of the {@code repository} value in a JSON issue object.
     */
    private static final String REPOSITORY = "repository";

    /**
     * Name of the {@code state} value in a JSON issue object.
     */
    private static final String STATE = "state";

    /**
     * Name of the {@code title} value in a JSON issue object.
     */
    private static final String TITLE = "title";

    /**
     * Name of the {@code updated_on} value in a JSON issue object.
     */
    private static final String UPDATED_ON = "updated_on";

    /**
     * Name of the {@code version} value in a JSON issue object.
     */
    private static final String VERSION = "version";

    /**
     * Name of the {@code votes} value in a JSON issue object.
     */
    private static final String VOTES = "votes";

    /**
     * Name of the {@code watches} value in a JSON issue object.
     */
    private static final String WATCHES = "watches";

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
    public static Function<JsonObject, BitbucketClientIssue> creator()
    {
        return creator(null);
    }

    /**
     * Returns a function to create an issue form a JSON object.
     *
     * @param bitbucketClient a Bitbucket API client
     * @return a function to create an issue from a JSON object
     */
    public static Function<JsonObject, BitbucketClientIssue> creator(
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
            // value = new ClientRepository(repository);
        }
        return value;
    }

    @Override
    public final int getId()
    {
        return getJsonObject().getInt(ID, 0);
    }

    @Override
    public final BitbucketUserAccount getReporter()
    {
        // This may be a JSON null.
        JsonValue reporter = getJsonObject().get(REPORTER);

        ClientUserAccount value = null;
        if (reporter != null && reporter != JsonValue.NULL) {
            // value = new ClientUserAccount((JsonObject) reporter);
        }
        return value;
    }

    @Override
    public final String getState()
    {
        return getJsonObject().getString(STATE, null);
    }

    @Override
    public final String getKind()
    {
        return getJsonObject().getString(KIND, null);
    }

    @Override
    public final String getPriority()
    {
        return getJsonObject().getString(PRIORITY, null);
    }

    @Override
    public final String getTitle()
    {
        return getJsonObject().getString(TITLE, null);
    }

    @Override
    public final BitbucketRendered getContent()
    {
        JsonObject content = getJsonObject().getJsonObject(CONTENT);

        BitbucketRendered value = null;
        if (content != null) {
            // value = new ClientRendered(content);
        }
        return value;
    }

    @Override
    public final BitbucketUserAccount getAssignee()
    {
        // This may be a JSON null.
        JsonValue assignee = getJsonObject().get(ASSIGNEE);

        ClientUserAccount value = null;
        if (assignee != null && assignee != JsonValue.NULL) {
            // value = new ClientUserAccount((JsonObject) assignee);
        }
        return value;
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
        JsonNumber votes = getJsonObject().getJsonNumber(VOTES);

        int value = -1;
        if (votes != null) {
            value = votes.intValue();
        }
        return value;
    }

    @Override
    public final int getWatches()
    {
        JsonNumber watches = getJsonObject().getJsonNumber(WATCHES);

        int value = -1;
        if (watches != null) {
            value = watches.intValue();
        }
        return value;
    }

    @Override
    public final Instant getCreated()
    {
        // This may be a JSON null.
        JsonValue createdOn = getJsonObject().get(CREATED_ON);

        Instant value = null;
        if (createdOn != null && createdOn != JsonValue.NULL) {
            value = toInstant((JsonString) createdOn);
        }
        return value;
    }

    @Override
    public final Instant getUpdated()
    {
        // This may be a JSON null.
        JsonValue updatedOn = getJsonObject().get(UPDATED_ON);

        Instant value = null;
        if (updatedOn != null && updatedOn != JsonValue.NULL) {
            value = toInstant((JsonString) updatedOn);
        }
        return value;
    }

    @Override
    public final Instant getEdited()
    {
        // This may be a JSON null.
        JsonValue editedOn = getJsonObject().get(EDITED_ON);

        Instant value = null;
        if (editedOn != null && editedOn != JsonValue.NULL) {
            value = toInstant((JsonString) editedOn);
        }
        return value;
    }
}
