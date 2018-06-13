/*
 * BitbucketEvent.java - class BitbucketEvent
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
import org.vx68k.bitbucket.api.client.BitbucketClientObject;
import org.vx68k.bitbucket.api.client.BitbucketClientUser;
import org.vx68k.bitbucket.api.client.Repository;

/**
 * Activity on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketEvent extends BitbucketClientObject
{
    /**
     * Name for the {@code actor} object in a JSON event object.
     */
    private static final String ACTOR = "actor";

    /**
     * Name for the {@code repository} object in a JSON event object.
     */
    private static final String REPOSITORY = "repository";

    /**
     * Name for the {@code push} object in a JSON event object.
     */
    private static final String PUSH = "push";

    /**
     * Constructs this event from a JSON event object.
     *
     * @param eventObject JSON event object
     * @exception IllegalArgumentException if the given object is {@code null}
     */
    public BitbucketEvent(final JsonObject eventObject)
    {
        super(eventObject);
    }

    /**
     * Returns the actor of this event as a {@link BitbucketUser} object.
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
     * Returns the repository of this event.
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
     * Returns the push description of this event.
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
