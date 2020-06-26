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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUserAccount;

/**
 * Unit tests for {@link BitbucketClient}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
class BitbucketClientTest
{
    /**
     * User UUID for tests.
     */
    private static final String USER_NAME = "{cebb58cd-f699-4393-8762-e0f743ccf770}";

    /**
     * Team UUID for tests.
     */
    private static final String TEAM_NAME = "{7590db3d-195a-40a0-aeab-1d8601a6298f}";

    private static final String REPOSITORY_OWNER_NAME = "vx68k";

    /**
     * Repository name for tests.
     */
    private static final String REPOSITORY_NAME = "bitbucket-api-client.java";

    /**
     * Tests {@link BitbucketClient#getUserAccount(String)}.
     */
    @Test
    void testGetUserAccount1()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketUserAccount user = client.getUserAccount(USER_NAME);
        System.out.println("Got " + user);
        assertNotNull(user.getUuid());
        assertNull(user.getName());
        assertNotNull(user.getDisplayName());
        assertNotNull(user.getCreated());
        // Other properties are unknown at test time.
    }

    /**
     * Tests {@link BitbucketClient#getUserAccount(String)}.
     */
    @Test
    void testGetUserAccount2()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketUserAccount user = client.getUserAccount(TEAM_NAME);
        assertNull(user);
    }

    /**
     * Tests {@link BitbucketClient#getTeamAccount(String)}.
     */
    @Test
    void testGetTeamAccount1()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketAccount team = client.getTeamAccount(TEAM_NAME);
        System.out.println("Got " + team);
        assertNotNull(team.getUuid());
        assertEquals("vx68k", team.getName());
        assertNotNull(team.getDisplayName());
        assertNotNull(team.getCreated());
        // Other properties are unknown at test time.
    }

    /**
     * Tests {@link BitbucketClient#getTeamAccount(String)}.
     */
    @Test
    void testGetTeamAccount2()
    {
        BitbucketClient client = new BitbucketClient();
        BitbucketAccount team = client.getTeamAccount(USER_NAME);
        assertNull(team);
    }

    /**
     * Tests {@link BitbucketClient#getRepository(String, String)}.
     */
    @Test
    void testGetRepository1()
    {
        BitbucketClient client = new BitbucketClient();

        // Case to be found.
        BitbucketRepository repository =
            client.getRepository(REPOSITORY_OWNER_NAME, REPOSITORY_NAME);
        System.out.println("Got " + repository);
        assertNotNull(repository.getOwner());
        assertEquals("vx68k", repository.getOwner().getName());
        assertEquals(REPOSITORY_NAME, repository.getName());
        assertNotNull(repository.getUuid());
        assertEquals(
            "vx68k" + "/" + REPOSITORY_NAME, repository.getFullName());
        assertEquals("git", repository.getScm());
        assertFalse(repository.isPrivate());
    }

    /**
     * Tests {@link BitbucketClient#getRepository(String, String)}.
     */
    @Test
    void testGetRepository2()
    {
        BitbucketClient bitbucketClient = new BitbucketClient();

        BitbucketRepository repository1 =
            bitbucketClient.getRepository(REPOSITORY_OWNER_NAME, "non-existent");
        assertNull(repository1);
    }
}
