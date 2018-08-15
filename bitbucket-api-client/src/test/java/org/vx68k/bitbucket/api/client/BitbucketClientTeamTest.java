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
     * Tests the constructor with an untyped object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUntyped()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        new BitbucketClientTeam(builder.build());
        fail();
    }

    /**
     * Tests the constructor with an object of a wrong type.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrongType()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("type", "not_user");
        new BitbucketClientTeam(builder.build());
        fail();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor()
    {
        JsonObject object = createTeamObjectBuilder().build();
        BitbucketClientTeam team = new BitbucketClientTeam(object);
        assertNull(team.getBitbucketClient());
//        assertNull(team.getUUID());
//        assertNull(team.getDisplayName());
//        assertNull(team.getWebsite());
//        assertNull(team.getLocation());
//        assertNull(team.getCreated());
//        assertNull(team.getLinks());

        try {
            new BitbucketClientTeam(null);
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
}
