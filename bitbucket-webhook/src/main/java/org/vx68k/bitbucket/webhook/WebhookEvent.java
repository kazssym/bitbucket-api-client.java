/*
 * WebhookEvent.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUserAccount;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * Class of webhook event on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class WebhookEvent
{
    private BitbucketRepository repository;

    private BitbucketUserAccount actor;

    private BitbucketPush push;

    /**
     * Constructs a webhook event.
     */
    public WebhookEvent()
    {
        // Nothing to do.
    }

    /**
     * Constructs a webhook event copying another.
     *
     * @param other another webhook event
     */
    public WebhookEvent(final WebhookEvent other)
    {
        this.repository = BitbucketClient.newRepository(other.getRepository());
        this.actor = BitbucketClient.newUserAccount(other.getActor());
        this.push = push; // TODO: Make a copy.
    }

    /**
     * Returns the repository of the event.
     *
     * @return the repository of the event
     */
    public final BitbucketRepository getRepository()
    {
        return repository;
    }

    public final void setRepository(final BitbucketRepository repository)
    {
        this.repository = BitbucketClient.newRepository(repository);
    }

    /**
     * Returns the actor of this event.
     *
     * @return the actor
     */
    public final BitbucketUserAccount getActor()
    {
        return actor;
    }

    public final void setActor(final BitbucketUserAccount actor)
    {
        this.actor = BitbucketClient.newUserAccount(actor);
    }

    /**
     * Returns the push description of this event.
     *
     * @return the push description
     */
    public final BitbucketPush getPush()
    {
        return push;
    }

    public final void setPush(final BitbucketPush push)
    {
        this.push = push; // TODO: Make a copy.
    }
}
