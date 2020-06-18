/*
 * ClientTeamAccountTest.java
 * Copyright (C) 2018-2020 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.vx68k.bitbucket.client.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * Unit tests for {@link ClientTeamAccount}.
 *
 * @author Kaz Nishimura
 */
public final class ClientTeamAccountTest
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
    public void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/team1.json");
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
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertEquals(ClientAccount.AccountType.TEAM, team1.getType());

        String string2 = "{\"type\":\"user\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(ClientAccount.AccountType.TEAM, team2.getType());
    }

    /**
     * Tests {@link ClientTeamAccount#getUuid()}.
     */
    @Test
    public void testUuid()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getUuid());

        String string2 = "{\"type\":\"team\",\"uuid\":\"{01234567-89ab-cdef-0123-456789abcdef}\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(UUID.fromString("01234567-89ab-cdef-0123-456789abcdef"), team2.getUuid());

        ClientTeamAccount team3 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals(SAMPLE1_UUID, team3.getUuid());
    }

    /**
     * Tests {@link ClientTeamAccount#getName()}.
     */
    @Test
    public void testName()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getName());

        String string2 = "{\"type\":\"team\",\"username\":\".name\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(".name", team2.getName());

        ClientTeamAccount team3 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals("vx68k", team3.getName());
    }

    /**
     * Tests {@link ClientTeamAccount#getDisplayName()}.
     */
    @Test
    public void testDisplayName()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getDisplayName());

        String string2 = "{\"type\":\"team\",\"display_name\":\".displayName\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(".displayName", team2.getDisplayName());

        ClientTeamAccount team3 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals("VX68k.org", team3.getDisplayName());
    }

    /**
     * Tests {@link ClientTeamAccount#getWebsite()}.
     */
    @Test
    public void testWebsite()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getWebsite());

        String string2 = "{\"type\":\"team\",\"website\":\".website\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(".website", team2.getWebsite());

        ClientTeamAccount team3 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertNull(team3.getWebsite());
    }

    /**
     * Tests {@link ClientTeamAccount#getLocation()}.
     */
    @Test
    public void testLocation()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getLocation());

        String string2 = "{\"type\":\"team\",\"location\":\".location\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(".location", team2.getLocation());

        ClientTeamAccount team3 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertNull(team3.getLocation());
    }

    /**
     * Tests {@link ClientTeamAccount#getCreated()}.
     */
    @Test
    public void testCreated()
    {
        String string1 = "{\"type\":\"team\"}";
        ClientTeamAccount team1 = jsonb.fromJson(string1, ClientTeamAccount.class);
        assertNull(team1.getCreated());

        String string2 = "{\"type\":\"team\",\"created_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientTeamAccount team2 = jsonb.fromJson(string2, ClientTeamAccount.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), team2.getCreated());

        ClientTeamAccount team3 = jsonb.fromJson(sample1, ClientTeamAccount.class);
        assertEquals(SAMPLE1_CREATED, team3.getCreated());
    }
}
