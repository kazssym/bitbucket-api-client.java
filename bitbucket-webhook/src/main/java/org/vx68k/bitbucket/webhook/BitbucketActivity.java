/*
 * BitbucketActivity.java - class BitbucketActivity
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

import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.BitbucketClientUser;
import org.vx68k.bitbucket.api.client.Repository;

/**
 * Activity on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketActivity
{
    /**
     * Name of the {@code actor} object in a JSON event object.
     */
    public static final String ACTOR = "actor";

    /**
     * Name of the {@code repository} object in a JSON event object.
     */
    public static final String REPOSITORY = "repository";

    /**
     * Name of the {@code push} object in a JSON event object.
     */
    public static final String PUSH = "push";

    /**
     * JSON event object given to the constructor.
     */
    private final JsonObject jsonObject;

    /**
     * Constructs this activity from a JSON event object.
     *
     * @param eventObject JSON event object
     * @exception IllegalArgumentException if the given object is {@code null}
     */
    protected BitbucketActivity(final JsonObject eventObject)
    {
        if (eventObject == null) {
            throw new IllegalArgumentException("JSON object is null");
        }
        jsonObject = eventObject;
    }

    /**
     * Returns the JSON event object given to the constructor.
     *
     * @return the JSON event object
     */
    public final JsonObject getJsonObject()
    {
        return jsonObject;
    }

    /**
     * Returns the actor of this activity as a {@link BitbucketUser} object.
     *
     * @return the actor
     */
    public final BitbucketUser getActor()
    {
        JsonObject eventObject = getJsonObject();
        BitbucketUser actor = null;
        if (eventObject.containsKey(ACTOR)) {
            actor = new BitbucketClientUser(eventObject.getJsonObject(ACTOR));
        }
        return actor;
    }

    /**
     * Returns the repository of this activity.
     *
     * @return the repository
     */
    public final Repository getRepository()
    {
        JsonObject eventObject = getJsonObject();
        Repository repository = null;
        if (eventObject.containsKey(REPOSITORY)) {
            repository = new Repository(eventObject.getJsonObject(REPOSITORY));
        }
        return repository;
    }

    /**
     * Returns the push description of this activity.
     *
     * @return the push description
     */
    public final BitbucketPush getPush()
    {
        JsonObject eventObject = getJsonObject();
        BitbucketPush push = null;
        if (eventObject.containsKey(PUSH)) {
            push = new BitbucketPush(eventObject.getJsonObject(PUSH));
        }
        return push;
    }
}
