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

package org.vx68k.bitbucket.api.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.BitbucketClientAccount;

/**
 * Unit tests for {@link BitbucketClientAccount}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientAccountTest
{
    /**
     * UUID for tests.
     */
    static final UUID TEST_UUID =
        UUID.fromString("01234567-89ab-cdef-0123-456789abcdef");

    /**
     * Adds the {@code "team"} type to a JSON object builder.
     *
     * @param builder a JSON object builder
     * @return the same JSON object builder
     */
    static JsonObjectBuilder addTeamType(final JsonObjectBuilder builder)
    {
        return builder.add("type", "team");
    }

    /**
     * Tests the constructors.
     */
    @Test
    public void testConstructors()
    {
        // NOTE: A builder can be reused.
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketClientAccount user1 =
            new BitbucketClientAccount(builder.add("type", "user").build());
        assertNull(user1.getBitbucketClient());

        BitbucketClientAccount team1 =
            new BitbucketClientAccount(builder.add("type", "team").build());
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

        addTeamType(builder);
        assertNull(new BitbucketClientAccount(builder.build()).getName());

        addTeamType(builder).add("username", "test");
        assertEquals(
            "test", new BitbucketClientAccount(builder.build()).getName());
    }

    /**
     * Tests {@link BitbucketClientAccount#getUUID()}.
     */
    @Test
    public void testGetUUID()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        addTeamType(builder);
        assertNull(new BitbucketClientAccount(builder.build()).getUUID());

        addTeamType(builder).add("uuid", "{" + TEST_UUID.toString() + "}");
        assertEquals(
            TEST_UUID, new BitbucketClientAccount(builder.build()).getUUID());
    }

    /**
     * Tests {@link BitbucketClientAccount#getDisplayName()}.
     */
    @Test
    public void testGetDisplayName()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        addTeamType(builder);
        assertNull(
            new BitbucketClientAccount(builder.build()).getDisplayName());

        addTeamType(builder).add("display_name", "Test Account");
        assertEquals(
            "Test Account",
            new BitbucketClientAccount(builder.build()).getDisplayName());
    }

    /**
     * Tests {@link BitbucketClientAccount#getWebsite()}.
     */
    @Test
    public void testGetWebsite()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        addTeamType(builder);
        assertNull(new BitbucketClientAccount(builder.build()).getWebsite());

        addTeamType(builder).add("website", "http://example.com/");
        assertEquals(
            "http://example.com/",
            new BitbucketClientAccount(builder.build()).getWebsite());
    }

    /**
     * Tests {@link BitbucketClientAccount#getLocation()}.
     */
    @Test
    public void testGetLocation()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        addTeamType(builder);
        assertNull(new BitbucketClientAccount(builder.build()).getLocation());

        addTeamType(builder).add("location", "Somewhere");
        assertEquals(
            "Somewhere",
            new BitbucketClientAccount(builder.build()).getLocation());
    }

    /**
     * Tests {@link BitbucketClientAccount#getCreated()}.
     */
    @Test
    public void testGetCreated()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        addTeamType(builder);
        assertNull(new BitbucketClientAccount(builder.build()).getCreated());

        addTeamType(builder).add(
            "created_on", "2001-01-01T01:23:45.678901+00:00");
        assertEquals(
            Instant.parse("2001-01-01T01:23:45.678901Z"),
            new BitbucketClientAccount(builder.build()).getCreated());
    }
}
