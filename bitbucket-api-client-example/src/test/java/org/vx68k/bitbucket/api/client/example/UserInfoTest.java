/*
 * UserInfoTest.jar - class UserInfoTest
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

package org.vx68k.bitbucket.api.client.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for {@link UserInfo}.
 *
 * @author Kaz Nishimura
 */
public final class UserInfoTest
{
    /**
     * {@link UserContext} object for each test.
     */
    private UserContext userContext = null;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp()
    {
        userContext = new UserContext();
    }

    /**
     * Tears down each test.
     */
    @After
    public void tearDown()
    {
        userContext = null;
    }

    /**
     * Tests {@link UserInfo#getUserContext getUserContext}.
     */
    @Test
    public void testGetUserContext()
    {
        UserInfo userInfo = new UserInfo(userContext);
        assertEquals(userContext, userInfo.getUserContext());
    }

    /**
     * Tests{@link UserInfo#getName getName}.
     */
    @Test
    public void testGetName()
    {
        UserInfo userInfo = new UserInfo(userContext);

        userInfo.setName("");
        assertEquals("", userInfo.getName());

        userInfo.setName("user");
        assertEquals("user", userInfo.getName());
    }

    /**
     * Tests {@link UserInfo#lookUp lookUp}.
     */
    @Ignore("Faces runtime is required")
    @Test
    public void testLookUp()
    {
        UserInfo userInfo = new UserInfo(userContext);
        Object outcome;

        userInfo.setName("");
        outcome = userInfo.lookUp();
        assertNull(outcome);
        assertNull(userInfo.getFoundUser());
        assertFalse(userInfo.isFound());

        userInfo.setName("kazssym");
        outcome = userInfo.lookUp();
        assertNull(outcome);
        assertNotNull(userInfo.getFoundUser());
        assertTrue(userInfo.isFound());
        assertEquals("kazssym", userInfo.getFoundUser().getName());
    }
}
