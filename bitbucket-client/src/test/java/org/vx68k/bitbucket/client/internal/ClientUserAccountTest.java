/*
 * ClientUserAccountTest.java
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

package org.vx68k.bitbucket.client.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientUserAccount}.
 *
 * @author Kaz Nishimura
 */
final class ClientUserAccountTest
{
    /**
     * UUID of the sample user 1.
     */
    private static final UUID SAMPLE1_UUID =
        UUID.fromString("cebb58cd-f699-4393-8762-e0f743ccf770");

    /**
     * Time when the sample team 1 created.
     */
    private static final OffsetDateTime SAMPLE1_CREATED =
        OffsetDateTime.parse("2010-02-25T11:30:06.496110Z");

    private Jsonb jsonb;

    private InputStream sample1;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/user1.json");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        sample1.close();
        sample1 = null;

        jsonb.close();
        jsonb = null;
    }

    /**
     * Tests {@link ClientUserAccount#getType()}.
     */
    @Test
    void testType1()
    {
        String string1 = "{}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getType());
    }

    /**
     * Tests {@link ClientUserAccount#getType()}.
     */
    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(ClientAccount.AccountType.USER, user1.getType());
    }

    /**
     * Tests {@link ClientUserAccount#getType()}.
     */
    @Test
    void testType3()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientUserAccount user1 = null;
        try {
            user1 = jsonb.fromJson(string1, ClientUserAccount.class);
            fail();
        }
        catch (final RuntimeException exception) {
            System.err.println("Caught as expected: " + exception.toString());
        }
        assertNull(user1);
    }

    /**
     * Tests {@link ClientUserAccount#getType()}.
     */
    @Test
    void testType4()
    {
        String string1 = "{\"type\":\"other\"}";
        ClientUserAccount user1 = null;
        try {
            user1 = jsonb.fromJson(string1, ClientUserAccount.class);
            fail();
        }
        catch (final RuntimeException exception) {
            System.err.println("Caught as expected: " + exception.toString());
        }
        assertNull(user1);
    }

    /**
     * Tests {@link ClientUserAccount#getUuid()}.
     */
    @Test
    void testUuid1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getUuid());
    }

    /**
     * Tests {@link ClientUserAccount#getUuid()}.
     */
    @Test
    void testUuid2()
    {
        String string1 = "{\"type\":\"user\",\"uuid\":\"{01234567-89ab-cdef-0123-456789abcdef}\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(UUID.fromString("01234567-89ab-cdef-0123-456789abcdef"), user1.getUuid());
    }

    /**
     * Tests {@link ClientUserAccount#getUuid()}.
     */
    @Test
    void testUuid3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals(SAMPLE1_UUID, user1.getUuid());
    }

    /**
     * Tests {@link ClientUserAccount#getName()}.
     */
    @Test
    void testName1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getName());
    }

    /**
     * Tests {@link ClientUserAccount#getName()}.
     */
    @Test
    void testName2()
    {
        String string1 = "{\"type\":\"user\",\"username\":\".name\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(".name", user1.getName());
    }

    /**
     * Tests {@link ClientUserAccount#getName()}.
     */
    @Test
    void testName3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNull(user1.getName());
    }

    /**
     * Tests {@link ClientUserAccount#getDisplayName()}.
     */
    @Test
    void testDisplayName1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getDisplayName());
    }

    /**
     * Tests {@link ClientUserAccount#getDisplayName()}.
     */
    @Test
    void testDisplayName2()
    {
        String string1 = "{\"type\":\"user\",\"display_name\":\".displayName\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(".displayName", user1.getDisplayName());
    }

    /**
     * Tests {@link ClientUserAccount#getDisplayName()}.
     */
    @Test
    void testDisplayName3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals("Kaz Nishimura", user1.getDisplayName());
    }

    /**
     * Tests {@link ClientUserAccount#getWebsite()}.
     */
    @Test
    void testWebsite1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getWebsite());
    }

    /**
     * Tests {@link ClientUserAccount#getWebsite()}.
     */
    @Test
    void testWebsite2()
    {
        String string1 = "{\"type\":\"user\",\"website\":\".website\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(".website", user1.getWebsite());
    }

    /**
     * Tests {@link ClientUserAccount#getWebsite()}.
     */
    @Test
    void testWebsite3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNull(user1.getWebsite());
    }

    /**
     * Tests {@link ClientUserAccount#getLocation()}.
     */
    @Test
    void testLocation1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getLocation());
    }

    /**
     * Tests {@link ClientUserAccount#getLocation()}.
     */
    @Test
    void testLocation2()
    {
        String string1 = "{\"type\":\"user\",\"location\":\".location\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(".location", user1.getLocation());
    }

    /**
     * Tests {@link ClientUserAccount#getLocation()}.
     */
    @Test
    void testLocation3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNull(user1.getLocation());
    }

    /**
     * Tests {@link ClientUserAccount#getCreated()}.
     */
    @Test
    void testCreated1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getCreated());
    }

    /**
     * Tests {@link ClientUserAccount#getCreated()}.
     */
    @Test
    void testCreated2()
    {
        String string1 = "{\"type\":\"user\",\"created_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), user1.getCreated());
    }

    /**
     * Tests {@link ClientUserAccount#getCreated()}.
     */
    @Test
    void testCreated3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals(SAMPLE1_CREATED, user1.getCreated());
    }

    /**
     * Tests {@link ClientUserAccount#isStaff()}.
     */
    @Test
    void testStaff1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(false, user1.isStaff());
    }

    /**
     * Tests {@link ClientUserAccount#isStaff()}.
     */
    @Test
    void testStaff2()
    {
        String string1 = "{\"type\":\"user\",\"is_staff\":true}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(true, user1.isStaff());
    }

    /**
     * Tests {@link ClientUserAccount#isStaff()}.
     */
    @Test
    void testStaff3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals(false, user1.isStaff());
    }

    /**
     * Tests {@link ClientUserAccount#getAccountId()}.
     */
    @Test
    void testAccountId1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getAccountId());
    }

    /**
     * Tests {@link ClientUserAccount#getAccountId()}.
     */
    @Test
    void testAccountId2()
    {
        String string1 = "{\"type\":\"user\",\"account_id\":\".accountId\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(".accountId", user1.getAccountId());
    }

    /**
     * Tests {@link ClientUserAccount#getAccountId()}.
     */
    @Test
    void testAccountId3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNotNull(user1.getAccountId());
    }

    @Test
    void testLinks1()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getLinks());
    }

    @Test
    void testLinks2()
    {
        String string1 = "{\"type\":\"user\",\"links\":{}}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNotNull(user1.getLinks());
        assertEquals(0, user1.getLinks().size());
    }

    @Test
    void testLinks3()
    {
        ClientUserAccount user1 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNotNull(user1.getLinks());
        assertNotEquals(0, user1.getLinks().size());
        assertNotNull(user1.getLinks().get("self"));
    }
}
