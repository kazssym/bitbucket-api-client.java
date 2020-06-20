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

    private WebhookPush push;

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
        BitbucketRepository repository = other.getRepository();
        if (repository != null) {
            repository = BitbucketClient.copyRepository(repository);
        }
        this.repository = repository;

        BitbucketUserAccount actor = other.getActor();
        if (actor != null) {
            actor = BitbucketClient.copyUserAccount(actor);
        }
        this.actor = actor;

        this.push = other.push; // TODO: Make a copy.
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

    public final void setRepository(BitbucketRepository repository)
    {
        if (repository != null) {
            repository = BitbucketClient.copyRepository(repository);
        }
        this.repository = repository;
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

    public final void setActor(BitbucketUserAccount actor)
    {
        if (actor != null) {
            actor = BitbucketClient.copyUserAccount(actor);
        }
        this.actor = actor;
    }

    /**
     * Returns the push description of this event.
     *
     * @return the push description
     */
    public final WebhookPush getPush()
    {
        return push;
    }

    public final void setPush(final WebhookPush push)
    {
        this.push = push; // TODO: Make a copy.
    }
}
