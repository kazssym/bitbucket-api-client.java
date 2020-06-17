/*
 * BitbucketClientAccountTest.java
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.junit.Ignore;
import org.junit.Test;
import org.vx68k.bitbucket.BitbucketAccount;

/**
 * Unit tests for {@link ClientAccount}.
 *
 * @author Kaz Nishimura
 */
public final class ClientAccountTest
{
    /**
     * UUID of the sample team 1.
     */
    private static final UUID SAMPLE_TEAM1_UUID =
        UUID.fromString("7590db3d-195a-40a0-aeab-1d8601a6298f");

    /**
     * Time when the sample team 1 created.
     */
    private static final Instant SAMPLE_TEAM1_CREATED =
        Instant.parse("2016-10-20T21:55:54.661382Z");

    /**
     * Sample JSON object for a team account.
     */
    private JsonObject sampleTeam1 = null;

    /**
     * Initializes the object.
     */
    public ClientAccountTest()
    {
        try (JsonReader reader = Json.createReader(
            getClass().getResourceAsStream("samples/team1.json"))) {
            sampleTeam1 = reader.readObject();
        }
    }

    /**
     * Tests the constructors.
     */
    @Ignore
    @Test
    public void testConstructors()
    {
        // NOTE: A builder can be reused.
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // Case with a JSON object of a wrong type.
        try {
            new ClientTeamAccount(builder.add("type", "other").build());
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }

        // Case with an empty JSON object.
        try {
            new ClientTeamAccount(builder.build());
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }

        // Case with a null pointer.
        try {
            new ClientTeamAccount((JsonObject) null);
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }
    }

    /**
     * Tests {@link ClientAccount#getName()}.
     */
    @Ignore
    @Test
    public void testGetName()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new ClientUserAccount(
            builder.add("type", "user").build());
        assertNull(user1.getName());

        BitbucketAccount team1 = new ClientTeamAccount(sampleTeam1);
        assertEquals("vx68k", team1.getName());
    }

    /**
     * Tests {@link ClientAccount#getUUID()}.
     */
    @Ignore
    @Test
    public void testGetUUID()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new ClientUserAccount(
            builder.add("type", "user").build());
        assertNull(user1.getUUID());

        BitbucketAccount team1 = new ClientTeamAccount(sampleTeam1);
        assertEquals(SAMPLE_TEAM1_UUID, team1.getUUID());
    }

    /**
     * Tests {@link ClientAccount#getDisplayName()}.
     */
    @Ignore
    @Test
    public void testGetDisplayName()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new ClientUserAccount(
            builder.add("type", "user").build());
        assertNull(user1.getDisplayName());

        BitbucketAccount team1 = new ClientTeamAccount(sampleTeam1);
        assertEquals("VX68k.org", team1.getDisplayName());
    }

    /**
     * Tests {@link ClientAccount#getWebsite()}.
     */
    @Ignore
    @Test
    public void testGetWebsite()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new ClientUserAccount(
            builder.add("type", "user").build());
        assertNull(user1.getWebsite());

        BitbucketAccount team1 = new ClientTeamAccount(sampleTeam1);
        assertNull(team1.getWebsite());
    }

    /**
     * Tests {@link ClientAccount#getLocation()}.
     */
    @Ignore
    @Test
    public void testGetLocation()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new ClientUserAccount(
            builder.add("type", "user").build());
        assertNull(user1.getLocation());

        BitbucketAccount team1 = new ClientTeamAccount(sampleTeam1);
        assertEquals(null, team1.getLocation());
    }

    /**
     * Tests {@link ClientAccount#getCreated()}.
     */
    @Ignore
    @Test
    public void testGetCreated()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new ClientUserAccount(
            builder.add("type", "user").addNull("created_on").build());
        assertNull(user1.getCreated());

        BitbucketAccount team1 = new ClientTeamAccount(sampleTeam1);
        assertEquals(SAMPLE_TEAM1_CREATED, team1.getCreated());
    }
}
