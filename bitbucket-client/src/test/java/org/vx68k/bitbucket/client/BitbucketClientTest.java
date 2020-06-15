/*
 * BitbucketClientTest.java - class BitbucketClientTest
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

package org.vx68k.bitbucket.client;

import org.vx68k.bitbucket.client.BitbucketClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUser;

/**
 * Unit tests for {@link BitbucketClient}.
 *
 * @author Kaz Nishimura
 */
public class BitbucketClientTest
{
    /**
     * User UUID for tests.
     */
    private static final String USER_UUID =
        "{cebb58cd-f699-4393-8762-e0f743ccf770}";

    /**
     * Team UUID for tests.
     */
    private static final String TEAM_UUID =
        "{7590db3d-195a-40a0-aeab-1d8601a6298f}";

    /**
     * Repository name for tests.
     */
    private static final String REPOSITORY_NAME = "bitbucket-api-client.java";

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
        BitbucketUser user = (BitbucketUser) client.getUser(USER_UUID);
        System.out.println("Got " + user);
        assertNotNull(user.getUUID());
        assertNull(user.getName());
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
        BitbucketAccount user = client.getUser(TEAM_UUID);
        assertNull(user);
    }

    /**
     * Tests {@link BitbucketClient#getTeam getTeam} with an existing team.
     */
    @Test
    public void testGetTeam()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketAccount team = client.getTeam(TEAM_UUID);
        System.out.println("Got " + team);
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
        BitbucketAccount team = client.getTeam(USER_UUID);
        assertNull(team);
    }

    /**
     * Tests {@link BitbucketClient#getRepository getRepository}.
     */
    @Test
    public void testGetRepository()
    {
        BitbucketClient client = new BitbucketClient();

        // Case to be found.
        BitbucketRepository repository =
            client.getRepository(TEAM_UUID, REPOSITORY_NAME);
        System.out.println("Got " + repository);
        assertNotNull(repository.getOwner());
        assertEquals("vx68k", repository.getOwner().getName());
        assertEquals(REPOSITORY_NAME, repository.getName());
        assertNotNull(repository.getUUID());
        assertEquals(
            "vx68k" + "/" + REPOSITORY_NAME, repository.getFullName());
        assertEquals("git", repository.getSCM());
        assertFalse(repository.isPrivate());

        // Case not to be found.
        assertNull(client.getRepository(TEAM_UUID, "non-existent"));
    }
}
