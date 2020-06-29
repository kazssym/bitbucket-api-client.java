/*
 * BitbucketWebhookPushTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BitbucketWebhookPush}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class BitbucketWebhookPushTest
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
     * Tests {@link BitbucketWebhookPush#getChanges()}.
     */
    @Test
    void testChanges1()
    {
        String string1 = "{}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNull(push1.getChanges());
    }

    /**
     * Tests {@link BitbucketWebhookPush#getChanges()}.
     */
    @Test
    void testChanges2()
    {
        String string1 = "{\"changes\":[]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(0, push1.getChanges().length);
    }

    /**
     * Tests {@link BitbucketWebhookPush#getChanges()}.
     */
    @Test
    void testChanges3()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(1, push1.getChanges().length);
        assertNotNull(push1.getChanges()[0]);
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isCreated()}.
     */
    @Test
    void testChangeCreated1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(false, push1.getChanges()[0].isCreated());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isCreated()}.
     */
    @Test
    void testChangeCreated2()
    {
        String string1 = "{\"changes\":[{\"created\":true}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(true, push1.getChanges()[0].isCreated());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isClosed()}.
     */
    @Test
    void testChangeClosed1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(false, push1.getChanges()[0].isClosed());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isClosed()}.
     */
    @Test
    void testChangeClosed2()
    {
        String string1 = "{\"changes\":[{\"closed\":true}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(true, push1.getChanges()[0].isClosed());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isForced()}.
     */
    @Test
    void testChangeForced1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(false, push1.getChanges()[0].isForced());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isForced()}.
     */
    @Test
    void testChangeForced2()
    {
        String string1 = "{\"changes\":[{\"forced\":true}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(true, push1.getChanges()[0].isForced());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isTruncated()}.
     */
    @Test
    void testChangeTruncated1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(false, push1.getChanges()[0].isTruncated());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#isTruncated()}.
     */
    @Test
    void testChangeTruncated2()
    {
        String string1 = "{\"changes\":[{\"truncated\":true}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertEquals(true, push1.getChanges()[0].isTruncated());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getOld()}.
     */
    @Test
    void testChangeOld1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNull(push1.getChanges()[0].getOld());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getOld()}.
     */
    @Test
    void testChangeOld2()
    {
        String string1 = "{\"changes\":[{\"old\":{}}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNotNull(push1.getChanges()[0].getOld());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getNew()}.
     */
    @Test
    void testChangeNew1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNull(push1.getChanges()[0].getNew());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getNew()}.
     */
    @Test
    void testChangeNew2()
    {
        String string1 = "{\"changes\":[{\"new\":{}}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNotNull(push1.getChanges()[0].getNew());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getCommits()}.
     */
    @Test
    void testChangeCommits1()
    {
        String string1 = "{\"changes\":[{}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNull(push1.getChanges()[0].getCommits());
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getCommits()}.
     */
    @Test
    void testChangeCommits2()
    {
        String string1 = "{\"changes\":[{\"commits\":[]}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNotNull(push1.getChanges()[0].getCommits());
        assertEquals(0, push1.getChanges()[0].getCommits().length);
    }

    /**
     * Tests {@link BitbucketWebhookPush.Change#getCommits()}.
     */
    @Disabled("Not implemented")
    @Test
    void testChangeCommits3()
    {
        String string1 = "{\"changes\":[{\"commits\":[{}]}]}";
        BitbucketWebhookPush push1 = jsonb.fromJson(string1, BitbucketWebhookPush.class);
        assertNotNull(push1.getChanges()[0].getCommits());
        assertEquals(1, push1.getChanges()[0].getCommits().length);
        assertNotNull(push1.getChanges()[0].getCommits()[0]);
    }
}
