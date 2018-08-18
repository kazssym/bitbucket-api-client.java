/*
 * BitbucketClientTeamTest.java - class BitbucketClientTeamTest
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.junit.Test;

/**
 * Unit tests for {@link BitbucketClientTeam}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientTeamTest
{
    /**
     * Returns a JSON object builder which is typed for a team.
     *
     * @return a JSON object builder
     */
    protected static JsonObjectBuilder createTeamObjectBuilder()
    {
        JsonObjectBuilder value = Json.createObjectBuilder();
        value.add("type", "team");
        return value;
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getBitbucketClient());

        // Case with a null pointer.
        try {
            new BitbucketClientTeam(null);
            fail();
        }
        catch (final IllegalArgumentException exception) {
            System.out.println("Caught " + exception.toString());
        }

        // Case with an empty JSON object.
        JsonObject emptyObject = Json.createObjectBuilder().build();
        try {
            new BitbucketClientTeam(emptyObject);
            fail();
        }
        catch (final IllegalArgumentException exception) {
            System.out.println("Caught " + exception.toString());
        }

        // Case with a JSON object of a wrong type.
        JsonObject wrongObject = Json.createObjectBuilder()
            .add("type", "user").build();
        try {
            new BitbucketClientTeam(wrongObject);
            fail();
        }
        catch (final IllegalArgumentException exception) {
            System.out.println("Caught " + exception.toString());
        }
    }

    /**
     * Tests {@link BitbucketClientTeam#getName()}.
     */
    @Test
    public void testGetName()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getName());

        JsonObject object2 = createTeamObjectBuilder()
            .add("username", "testName").build();
        BitbucketClientTeam team2 = new BitbucketClientTeam(object2);
        assertEquals("testName", team2.getName());
    }

    /**
     * Tests {@link BitbucketClientTeam#getUUID()}.
     */
    @Test
    public void testGetUUID()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getUUID());

        JsonObject object2 = createTeamObjectBuilder()
            .add("uuid", "{01234567-89ab-cdef-0123-456789abcdef}").build();
        BitbucketClientTeam team2 = new BitbucketClientTeam(object2);
        assertEquals(
            UUID.fromString("01234567-89ab-cdef-0123-456789abcdef"),
            team2.getUUID());
    }

    /**
     * Tests {@link BitbucketClientTeam#getDisplayName()}.
     */
    @Test
    public void testGetDisplayName()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getDisplayName());

        JsonObject object2 = createTeamObjectBuilder()
            .add("display_name", "testName").build();
        BitbucketClientTeam team2 = new BitbucketClientTeam(object2);
        assertEquals("testName", team2.getDisplayName());
    }

    /**
     * Tests {@link BitbucketClientTeam#getWebsite()}.
     */
    @Test
    public void testGetWebsite()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getWebsite());

        JsonObject object2 = createTeamObjectBuilder()
            .add("website", "http://example.com/").build();
        BitbucketClientTeam team2 = new BitbucketClientTeam(object2);
        assertEquals("http://example.com/", team2.getWebsite());
    }

    /**
     * Tests {@link BitbucketClientTeam#getLocation()}.
     */
    @Test
    public void testGetLocation()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getLocation());

        JsonObject object2 = createTeamObjectBuilder()
            .add("location", "testLocation").build();
        BitbucketClientTeam team2 = new BitbucketClientTeam(object2);
        assertEquals("testLocation", team2.getLocation());
    }

    /**
     * Tests {@link BitbucketClientTeam#getCreated()}.
     */
    @Test
    public void testGetCreated()
    {
        JsonObject object1 = createTeamObjectBuilder().build();
        BitbucketClientTeam team1 = new BitbucketClientTeam(object1);
        assertNull(team1.getCreated());

        JsonObject object2 = createTeamObjectBuilder()
            .add("created_on", "2001-01-01T01:23:45.678Z").build();
        BitbucketClientTeam team2 = new BitbucketClientTeam(object2);
        assertEquals(
            Instant.parse("2001-01-01T01:23:45.678Z"), team2.getCreated());
    }
}
