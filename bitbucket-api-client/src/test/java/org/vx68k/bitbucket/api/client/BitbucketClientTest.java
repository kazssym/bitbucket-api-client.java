/*
 * BitbucketClientTest.java - class BitbucketClientTest
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Unit tests for {@link BitbucketClient}.
 *
 * @author Kaz Nishimura
 */
public class BitbucketClientTest
{
    /**
     * Tests the default constructor.
     */
    @Test
    public void testDefault()
    {
        BitbucketClient client = new BitbucketClient();
    }

    /**
     * Tests {@link BitbucketClient#getUser getUser} with an existing user.
     */
    @Test
    public void testGetUser()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketUser user = client.getUser("kazssym");
        System.out.println("Got " + user);
        assertEquals(BitbucketUser.USER, user.getType());
        assertNotNull(user.getUUID());
        assertEquals("kazssym", user.getName());
        assertNotNull(user.getDisplayName());
        assertNotNull(user.getCreated());
        // Other properties are unknown at test time.
    }

    /**
     * Tests {@link BitbucketClient#getUser getUser} with a non-existing user.
     */
    @Test
    public void testGetUserNotFound()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketUser user = client.getUser("vx68k"); // "vx68k" is a team.
        assertNull(user);
    }

    /**
     * Tests {@link BitbucketClient#getTeam getTeam} with an existing team.
     */
    @Test
    public void testGetTeam()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketUser team = client.getTeam("vx68k");
        System.out.println("Got " + team);
        assertEquals(BitbucketUser.TEAM, team.getType());
        assertNotNull(team.getUUID());
        assertEquals("vx68k", team.getName());
        assertNotNull(team.getDisplayName());
        assertNotNull(team.getCreated());
        // Other properties are unknown at test time.
    }

    /**
     * Tests {@link BitbucketClient#getUser getUser} with a non-existing user.
     */
    @Test
    public void testGetTeamNotFound()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketUser team = client.getTeam("kazssym");
        assertNull(team);
    }
}
