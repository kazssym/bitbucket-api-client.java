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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
public final class ClientUserAccountTest
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
    public void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/user1.json");
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        sample1.close();
        sample1 = null;

        jsonb.close();
        jsonb = null;
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor()
    {
        String string1 = "{}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(ClientAccount.AccountType.USER, user1.getType());

        String string2 = "{\"type\":\"team\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(ClientAccount.AccountType.USER, user2.getType());
    }

    /**
     * Tests {@link ClientUserAccount#getUuid}.
     */
    @Test
    public void testUuid()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getUuid());

        String string2 = "{\"type\":\"user\",\"uuid\":\"{01234567-89ab-cdef-0123-456789abcdef}\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(UUID.fromString("01234567-89ab-cdef-0123-456789abcdef"), user2.getUuid());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals(SAMPLE1_UUID, user3.getUuid());
    }

    /**
     * Tests {@link ClientUserAccount#getName}.
     */
    @Test
    public void testName()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getName());

        String string2 = "{\"type\":\"user\",\"username\":\".name\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(".name", user2.getName());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNull(user3.getName());
    }

    /**
     * Tests {@link ClientUserAccount#getDisplayName}.
     */
    @Test
    public void testDisplayName()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getDisplayName());

        String string2 = "{\"type\":\"user\",\"display_name\":\".displayName\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(".displayName", user2.getDisplayName());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals("Kaz Nishimura", user3.getDisplayName());
    }

    /**
     * Tests {@link ClientUserAccount#getWebsite}.
     */
    @Test
    public void testWebsite()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getWebsite());

        String string2 = "{\"type\":\"user\",\"website\":\".website\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(".website", user2.getWebsite());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNull(user3.getWebsite());
    }

    /**
     * Tests {@link ClientUserAccount#getLocation}.
     */
    @Test
    public void testLocation()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getLocation());

        String string2 = "{\"type\":\"user\",\"location\":\".location\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(".location", user2.getLocation());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNull(user3.getLocation());
    }

    /**
     * Tests {@link ClientUserAccount#getCreated}.
     */
    @Test
    public void testCreated()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getCreated());

        String string2 = "{\"type\":\"user\",\"created_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), user2.getCreated());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals(SAMPLE1_CREATED, user3.getCreated());
    }

    /**
     * Tests {@link ClientUserAccount#isStaff}.
     */
    @Test
    public void testStaff()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertEquals(false, user1.isStaff());

        String string2 = "{\"type\":\"user\",\"is_staff\":true}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(true, user2.isStaff());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertEquals(false, user3.isStaff());
    }

    /**
     * Tests {@link ClientUserAccount#getAccountId}.
     */
    @Test
    public void testAccountId()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientUserAccount user1 = jsonb.fromJson(string1, ClientUserAccount.class);
        assertNull(user1.getAccountId());

        String string2 = "{\"type\":\"user\",\"account_id\":\".accountId\"}";
        ClientUserAccount user2 = jsonb.fromJson(string2, ClientUserAccount.class);
        assertEquals(".accountId", user2.getAccountId());

        ClientUserAccount user3 = jsonb.fromJson(sample1, ClientUserAccount.class);
        assertNotNull(user3.getAccountId());
    }
}
