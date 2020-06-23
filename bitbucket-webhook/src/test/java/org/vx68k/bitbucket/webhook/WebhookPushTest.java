/*
 * WebhookPushTest.java
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
 * Unit tests for {@link WebhookPush}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class WebhookPushTest
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
     * Tests {@link WebhookPush#getChanges()}.
     */
    @Test
    void testChanges1()
    {
        String string1 = "{}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNull(push1.getChanges());
    }

    /**
     * Tests {@link WebhookPush#getChanges()}.
     */
    @Test
    void testChanges2()
    {
        String string1 = "{\"changes\":[]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(0, push1.getChanges().length);
    }

    /**
     * Tests {@link WebhookPush#getChanges()}.
     */
    @Test
    void testChanges3()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(1, push1.getChanges().length);
    }

    /**
     * Tests {@link WebhookPush.Change#isCreated()}.
     */
    @Test
    void testChangeCreated1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(false, push1.getChanges()[0].isCreated());
    }

    /**
     * Tests {@link WebhookPush.Change#isCreated()}.
     */
    @Test
    void testChangeCreated2()
    {
        String string1 = "{\"changes\":[{\"created\":true}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(true, push1.getChanges()[0].isCreated());
    }

    /**
     * Tests {@link WebhookPush.Change#isClosed()}.
     */
    @Test
    void testChangeClosed1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(false, push1.getChanges()[0].isClosed());
    }

    /**
     * Tests {@link WebhookPush.Change#isClosed()}.
     */
    @Test
    void testChangeClosed2()
    {
        String string1 = "{\"changes\":[{\"closed\":true}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(true, push1.getChanges()[0].isClosed());
    }

    /**
     * Tests {@link WebhookPush.Change#isForced()}.
     */
    @Test
    void testChangeForced1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(false, push1.getChanges()[0].isForced());
    }

    /**
     * Tests {@link WebhookPush.Change#isForced()}.
     */
    @Test
    void testChangeForced2()
    {
        String string1 = "{\"changes\":[{\"forced\":true}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(true, push1.getChanges()[0].isForced());
    }

    /**
     * Tests {@link WebhookPush.Change#isTruncated()}.
     */
    @Test
    void testChangeTruncated1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(false, push1.getChanges()[0].isTruncated());
    }

    /**
     * Tests {@link WebhookPush.Change#isTruncated()}.
     */
    @Test
    void testChangeTruncated2()
    {
        String string1 = "{\"changes\":[{\"truncated\":true}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertEquals(true, push1.getChanges()[0].isTruncated());
    }

    /**
     * Tests {@link WebhookPush.Change#getOld()}.
     */
    @Test
    void testChangeOld1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertNull(push1.getChanges()[0].getOld());
    }

    /**
     * Tests {@link WebhookPush.Change#getOld()}.
     */
    @Test
    void testChangeOld2()
    {
        String string1 = "{\"changes\":[{\"old\":{}}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertNotNull(push1.getChanges()[0].getOld());
    }

    /**
     * Tests {@link WebhookPush.Change#getNew()}.
     */
    @Test
    void testChangeNew1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertNull(push1.getChanges()[0].getNew());
    }

    /**
     * Tests {@link WebhookPush.Change#getNew()}.
     */
    @Test
    void testChangeNew2()
    {
        String string1 = "{\"changes\":[{\"new\":{}}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges());
        assertNotNull(push1.getChanges()[0].getNew());
    }

    /**
     * Tests {@link WebhookPush.Change#getCommits()}.
     */
    @Test
    void testChangeCommits1()
    {
        String string1 = "{\"changes\":[{}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges()[0]);
        assertNull(push1.getChanges()[0].getCommits());
    }

    /**
     * Tests {@link WebhookPush.Change#getCommits()}.
     */
    @Test
    void testChangeCommits2()
    {
        String string1 = "{\"changes\":[{\"commits\":[]}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges()[0]);
        assertNotNull(push1.getChanges()[0].getCommits());
        assertEquals(0, push1.getChanges()[0].getCommits().length);
    }

    /**
     * Tests {@link WebhookPush.Change#getCommits()}.
     */
    @Disabled("Not implemented")
    @Test
    void testChangeCommits3()
    {
        String string1 = "{\"changes\":[{\"commits\":[{}]}]}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNotNull(push1.getChanges()[0]);
        assertNotNull(push1.getChanges()[0].getCommits());
        assertEquals(1, push1.getChanges()[0].getCommits().length);
        assertNotNull(push1.getChanges()[0].getCommits()[0]);
    }
}
