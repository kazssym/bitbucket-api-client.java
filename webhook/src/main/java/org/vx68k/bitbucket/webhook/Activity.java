/*
 * Activity.java - class Activity
 * Copyright (C) 2015-2018 Kaz Nishimura
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
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.vx68k.bitbucket.webhook;

import java.util.logging.Logger;
import javax.json.JsonObject;
import org.vx68k.bitbucket.api.client.Repository;
import org.vx68k.bitbucket.api.client.BitbucketUser;

/**
 * Activity on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public abstract class Activity
{
    /**
     * Name of log message resources.
     */
    private static final String LOG_MESSAGES = "resources/LogMessages";

    /**
     * {@link JsonObject} object given to the constructor.
     */
    private final JsonObject jsonObject;

    /**
     * User who caused this activity.
     */
    private BitbucketUser actor;

    /**
     * Repository on which this activity occurred.
     */
    private Repository repository;

    /**
     * Constructs this activity from a {@link JsonObject} object.
     *
     * @param object {@link JsonObject} object
     */
    protected Activity(final JsonObject object)
    {
        jsonObject = object;
        actor = new BitbucketUser(
            object.getJsonObject(WebhookJsonKeys.ACTOR));
        repository = new Repository(
                object.getJsonObject(WebhookJsonKeys.REPOSITORY));
    }

    /**
     * Returns the {@link JsonObject} object given to the constructor.
     *
     * @return the {@link JsonObject} object
     */
    public final JsonObject getJsonObject()
    {
        return jsonObject;
    }

    /**
     * Returns the actor of this object.
     * @return actor
     */
    public final BitbucketUser getActor()
    {
        return actor;
    }

    /**
     * Returns the repository of this object.
     * @return repository
     */
    public final Repository getRepository()
    {
        return repository;
    }

    /**
     * Returns the logger for this package.
     * @return logger
     */
    protected static Logger getLogger()
    {
        return Logger.getLogger(
                Activity.class.getPackage().getName(), LOG_MESSAGES);
    }
}
