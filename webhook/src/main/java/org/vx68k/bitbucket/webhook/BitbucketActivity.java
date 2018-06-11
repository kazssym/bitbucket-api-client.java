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
public abstract class BitbucketActivity
{
    /**
     * Name of the {@code actor} object in a JSON object.
     */
    public static final String ACTOR = "actor";

    /**
     * Name of the {@code repository} object in a JSON object.
     */
    public static final String REPOSITORY = "repository";

    /**
     * JSON object given to the constructor.
     */
    private final JsonObject jsonObject;

    /**
     * Constructs this activity from a JSON object.
     *
     * @param activityObject JSON object
     * @exception IllegalArgumentException if the given JSON object is {@code
     * null}
     */
    protected BitbucketActivity(final JsonObject activityObject)
    {
        if (activityObject == null) {
            throw new IllegalArgumentException("JsonObject is null");
        }
        jsonObject = activityObject;
    }

    /**
     * Returns the JSON object given to the constructor.
     *
     * @return the JSON object
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
        JsonObject activityObject = getJsonObject();
        return new BitbucketClientUser(activityObject.getJsonObject(ACTOR));
    }

    /**
     * Returns the repository of this object.
     *
     * @return repository
     */
    public final Repository getRepository()
    {
        JsonObject activityObject = getJsonObject();
        return new Repository(activityObject.getJsonObject(REPOSITORY));
    }
}
