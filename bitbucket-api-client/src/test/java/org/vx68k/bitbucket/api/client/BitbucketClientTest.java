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
     * Tests {@link BitbucketClient#getUser(String)}.
     */
    @Test
    public void testGetUser()
    {
        BitbucketUser user = BitbucketClient.getUser("kazssym");
        System.out.println("Got " + user);
        assertEquals(BitbucketUser.USER, user.getType());
        assertNotNull(user.getUUID());
        assertEquals("kazssym", user.getName());
        assertNotNull(user.getDisplayName());
        assertNotNull(user.getCreated());
        // Other properties are unknown at test time.
    }
}
