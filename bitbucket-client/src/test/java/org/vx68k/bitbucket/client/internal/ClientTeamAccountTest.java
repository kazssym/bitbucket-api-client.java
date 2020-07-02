/*
 * ClientTeamAccountTest.java
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
 * Unit tests for {@link ClientTeamAccount}.
 *
 * @author Kaz Nishimura
 */
final class ClientTeamAccountTest
{
    /**
     * UUID of the sample team 1.
     */
    private static final UUID SAMPLE1_UUID =
        UUID.fromString("7590db3d-195a-40a0-aeab-1d8601a6298f");

    /**
     * Time when the sample team 1 created.
     */
    private static final OffsetDateTime SAMPLE1_CREATED =
        OffsetDateTime.parse("2016-10-20T21:55:54.661382Z");

    private Jsonb jsonb;

    private InputStream sample1;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/team1.json");
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
     * Tests {@link ClientTeamAccount#getType()}.
     */
    @Test
    void testType1()
    {
        String string1 = "{}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getType());
    }

    /**
     * Tests {@link ClientTeamAccount#getType()}.
     */
    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(ClientTeamAccount.TEAM, team1.getType());
    }

    /**
     * Tests {@link ClientTeamAccount#getType()}.
     */
    @Test
    void testType3()
    {
        String string1 = "{\"type\":\"user\"}";
        ClientTeamAccount team1 = null;
        try {
            team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
            fail();
        }
        catch (final RuntimeException exception) {
            System.err.println("Caught as expected: " + exception.toString());
        }
        assertNull(team1);
    }

    /**
     * Tests {@link ClientTeamAccount#getType()}.
     */
    @Test
    void testType4()
    {
        String string1 = "{\"type\":\"other\"}";
        ClientTeamAccount team1 = null;
        try {
            team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
            fail();
        }
        catch (final RuntimeException exception) {
            System.err.println("Caught as expected: " + exception.toString());
        }
        assertNull(team1);
    }

    /**
     * Tests {@link ClientTeamAccount#getUuid()}.
     */
    @Test
    void testUuid1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getUuid());
    }

    /**
     * Tests {@link ClientTeamAccount#getUuid()}.
     */
    @Test
    void testUuid2()
    {
        String string1 = "{\"type\":\"team\",\"uuid\":\"{01234567-89ab-cdef-0123-456789abcdef}\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(UUID.fromString("01234567-89ab-cdef-0123-456789abcdef"), team1.getUuid());
    }

    /**
     * Tests {@link ClientTeamAccount#getUuid()}.
     */
    @Test
    void testUuid3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals(SAMPLE1_UUID, team1.getUuid());
    }

    /**
     * Tests {@link ClientTeamAccount#getUsername()}.
     */
    @Test
    void testName1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getUsername());
    }

    /**
     * Tests {@link ClientTeamAccount#getUsername()}.
     */
    @Test
    void testName2()
    {
        String string1 = "{\"type\":\"team\",\"username\":\".name\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(".name", team1.getUsername());
    }

    /**
     * Tests {@link ClientTeamAccount#getUsername()}.
     */
    @Test
    void testName3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals("vx68k", team1.getUsername());
    }

    /**
     * Tests {@link ClientTeamAccount#getDisplayName()}.
     */
    @Test
    void testDisplayName1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getDisplayName());
    }

    /**
     * Tests {@link ClientTeamAccount#getDisplayName()}.
     */
    @Test
    void testDisplayName2()
    {
        String string1 = "{\"type\":\"team\",\"display_name\":\".displayName\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(".displayName", team1.getDisplayName());
    }

    /**
     * Tests {@link ClientTeamAccount#getDisplayName()}.
     */
    @Test
    void testDisplayName3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals("VX68k.org", team1.getDisplayName());
    }

    /**
     * Tests {@link ClientTeamAccount#getWebsite()}.
     */
    @Test
    void testWebsite1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getWebsite());
    }

    /**
     * Tests {@link ClientTeamAccount#getWebsite()}.
     */
    @Test
    void testWebsite2()
    {
        String string1 = "{\"type\":\"team\",\"website\":\".website\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(".website", team1.getWebsite());
    }

    /**
     * Tests {@link ClientTeamAccount#getWebsite()}.
     */
    @Test
    void testWebsite3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertNull(team1.getWebsite());
    }

    /**
     * Tests {@link ClientTeamAccount#getLocation()}.
     */
    @Test
    void testLocation1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getLocation());
    }

    /**
     * Tests {@link ClientTeamAccount#getLocation()}.
     */
    @Test
    void testLocation2()
    {
        String string1 = "{\"type\":\"team\",\"location\":\".location\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(".location", team1.getLocation());
    }

    /**
     * Tests {@link ClientTeamAccount#getLocation()}.
     */
    @Test
    void testLocation3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertNull(team1.getLocation());
    }

    /**
     * Tests {@link ClientTeamAccount#getCreated()}.
     */
    @Test
    void testCreated1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getCreated());
    }

    /**
     * Tests {@link ClientTeamAccount#getCreated()}.
     */
    @Test
    void testCreated2()
    {
        String string1 = "{\"type\":\"team\",\"created_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), team1.getCreated());
    }

    /**
     * Tests {@link ClientTeamAccount#getCreated()}.
     */
    @Test
    void testCreated3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals(SAMPLE1_CREATED, team1.getCreated());
    }

    @Test
    void testLinks1()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getLinks());
    }

    @Test
    void testLinks2()
    {
        String string1 = "{\"type\":\"team\",\"links\":{}}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNotNull(team1.getLinks());
        assertEquals(0, team1.getLinks().size());
    }

    @Test
    void testLinks3()
    {
        ClientTeamAccount team1 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertNotNull(team1.getLinks());
        assertNotEquals(0, team1.getLinks().size());
        assertNotNull(team1.getLinks().get("self"));
    }
}
