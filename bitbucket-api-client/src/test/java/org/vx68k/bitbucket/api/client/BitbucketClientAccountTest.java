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

package org.vx68k.bitbucket.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.junit.Ignore;
import org.junit.Test;
import org.vx68k.bitbucket.api.BitbucketAccount;
import org.vx68k.bitbucket.api.BitbucketRepository;

/**
 * Unit tests for {@link BitbucketClientAccount}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientAccountTest
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
    public BitbucketClientAccountTest()
    {
        try (JsonReader reader = Json.createReader(
            getClass().getResourceAsStream("samples/team1.json"))) {
            sampleTeam1 = reader.readObject();
        }
    }

    /**
     * Tests the constructors.
     */
    @Test
    public void testConstructors()
    {
        // NOTE: A builder can be reused.
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketClientAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").build());
        assertNull(user1.getBitbucketClient());

        BitbucketClientAccount team1 = new BitbucketClientAccount(
            builder.add("type", "team").build());
        assertNull(team1.getBitbucketClient());

        // Case with a JSON object of a wrong type.
        try {
            new BitbucketClientAccount(builder.add("type", "other").build());
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }

        // Case with an empty JSON object.
        try {
            new BitbucketClientAccount(builder.build());
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }

        // Case with a null pointer.
        try {
            new BitbucketClientAccount(null);
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }
    }

    /**
     * Tests {@link BitbucketClientAccount#getName()}.
     */
    @Test
    public void testGetName()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").build());
        assertNull(user1.getName());

        BitbucketAccount team1 = new BitbucketClientAccount(sampleTeam1);
        assertEquals("vx68k", team1.getName());
    }

    /**
     * Tests {@link BitbucketClientAccount#getUUID()}.
     */
    @Test
    public void testGetUUID()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").build());
        assertNull(user1.getUUID());

        BitbucketAccount team1 = new BitbucketClientAccount(sampleTeam1);
        assertEquals(SAMPLE_TEAM1_UUID, team1.getUUID());
    }

    /**
     * Tests {@link BitbucketClientAccount#getDisplayName()}.
     */
    @Test
    public void testGetDisplayName()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").build());
        assertNull(user1.getDisplayName());

        BitbucketAccount team1 = new BitbucketClientAccount(sampleTeam1);
        assertEquals("VX68k.org", team1.getDisplayName());
    }

    /**
     * Tests {@link BitbucketClientAccount#getWebsite()}.
     */
    @Test
    public void testGetWebsite()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").build());
        assertNull(user1.getWebsite());

        BitbucketAccount team1 = new BitbucketClientAccount(sampleTeam1);
        assertNull(team1.getWebsite());
    }

    /**
     * Tests {@link BitbucketClientAccount#getLocation()}.
     */
    @Test
    public void testGetLocation()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").build());
        assertNull(user1.getLocation());

        BitbucketAccount team1 = new BitbucketClientAccount(sampleTeam1);
        assertEquals(null, team1.getLocation());
    }

    /**
     * Tests {@link BitbucketClientAccount#getCreated()}.
     */
    @Test
    public void testGetCreated()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").addNull("created_on").build());
        assertNull(user1.getCreated());

        BitbucketAccount team1 = new BitbucketClientAccount(sampleTeam1);
        assertEquals(SAMPLE_TEAM1_CREATED, team1.getCreated());
    }

    /**
     * Tests {@link BitbucketClientAccount#getRepository(String)}.
     */
    @Ignore("Not implemented")
    @Test
    public void testGetRepository()
    {
        // @todo Implement this method.
        fail("Test not implemented");
    }

    /**
     * Tests {@link BitbucketClientAccount#repositories()}.
     */
    @Test
    public void testRepositories()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketAccount user1 = new BitbucketClientAccount(
            builder.add("type", "user").addNull("created_on").build(),
            BitbucketClient.getDefaultInstance());
        assertNull(user1.repositories());

        BitbucketAccount team1 = new BitbucketClientAccount(
            sampleTeam1, BitbucketClient.getDefaultInstance());
        Collection<BitbucketRepository> repositories = team1.repositories();
        assertNotNull(repositories);
        assertTrue(repositories.size() > 0);
    }
}
