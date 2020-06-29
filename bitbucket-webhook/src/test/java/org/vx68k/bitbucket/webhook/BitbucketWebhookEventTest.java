/*
 * WebhookEventTest.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BitbucketWebhookEvent}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class BitbucketWebhookEventTest
{
    private Jsonb jsonb;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();
    }

    @AfterEach
    void tearDown() throws Exception
    {
        jsonb.close();
        jsonb = null;
    }

    /**
     * Tests {@link BitbucketWebhookEvent#getRepository()}.
     */
    @Test
    void testRepository1()
    {
        String string1 = "{}";
        BitbucketWebhookEvent event1 = jsonb.fromJson(string1, BitbucketWebhookEvent.class);
        assertNull(event1.getRepository());
    }

    /**
     * Tests {@link BitbucketWebhookEvent#getRepository()}.
     */
    @Test
    void testRepository2()
    {
        String string1 = "{\"repository\":{}}";
        BitbucketWebhookEvent event1 = jsonb.fromJson(string1, BitbucketWebhookEvent.class);
        assertNotNull(event1.getRepository());
    }

    /**
     * Tests {@link BitbucketWebhookEvent#getActor()}.
     */
    @Test
    void testActor1()
    {
        String string1 = "{}";
        BitbucketWebhookEvent event1 = jsonb.fromJson(string1, BitbucketWebhookEvent.class);
        assertNull(event1.getActor());
    }

    /**
     * Tests {@link BitbucketWebhookEvent#getActor()}.
     */
    @Test
    void testActor2()
    {
        String string1 = "{\"actor\":{}}";
        BitbucketWebhookEvent event1 = jsonb.fromJson(string1, BitbucketWebhookEvent.class);
        assertNotNull(event1.getActor());
    }

    /**
     * Tests {@link BitbucketWebhookEvent#getPush()}.
     */
    @Test
    void testPush1()
    {
        String string1 = "{}";
        BitbucketWebhookEvent event1 = jsonb.fromJson(string1, BitbucketWebhookEvent.class);
        assertNull(event1.getPush());
    }

    /**
     * Tests {@link BitbucketWebhookEvent#getPush()}.
     */
    @Test
    void testPush2()
    {
        String string1 = "{\"push\":{}}";
        BitbucketWebhookEvent event1 = jsonb.fromJson(string1, BitbucketWebhookEvent.class);
        assertNotNull(event1.getPush());
    }
}
