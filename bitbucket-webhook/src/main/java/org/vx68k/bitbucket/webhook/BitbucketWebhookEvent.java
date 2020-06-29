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

import javax.json.bind.annotation.JsonbTypeAdapter;

import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUserAccount;
import org.vx68k.bitbucket.client.adapter.BitbucketRepositoryAdapter;
import org.vx68k.bitbucket.client.adapter.BitbucketUserAccountAdapter;

/**
 * Class of webhook event on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class BitbucketWebhookEvent
{
    private BitbucketRepository repository;

    private BitbucketUserAccount actor;

    private BitbucketWebhookPush push;

    /**
     * Constructs a webhook event.
     */
    public BitbucketWebhookEvent()
    {
        // Nothing to do.
    }

    /**
     * Constructs a webhook event copying another.
     *
     * @param other another webhook event
     */
    public BitbucketWebhookEvent(final BitbucketWebhookEvent other)
    {
        setRepository(other.repository);
        setActor(other.actor);
        setPush(other.push);
    }

    /**
     * Returns the repository of the event.
     *
     * @return the repository of the event
     */
    @JsonbTypeAdapter(BitbucketRepositoryAdapter.class)
    public final BitbucketRepository getRepository()
    {
        return repository;
    }

    public final void setRepository(BitbucketRepository repository)
    {
        this.repository = repository;
    }

    /**
     * Returns the actor of this event.
     *
     * @return the actor
     */
    @JsonbTypeAdapter(BitbucketUserAccountAdapter.class)
    public final BitbucketUserAccount getActor()
    {
        return actor;
    }

    public final void setActor(BitbucketUserAccount actor)
    {
        this.actor = actor;
    }

    /**
     * Returns the push description of this event.
     *
     * @return the push description
     */
    public final BitbucketWebhookPush getPush()
    {
        return push;
    }

    public final void setPush(final BitbucketWebhookPush push)
    {
        this.push = push;
    }
}
