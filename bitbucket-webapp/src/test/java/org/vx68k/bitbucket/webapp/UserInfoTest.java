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

package org.vx68k.bitbucket.webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * Unit tests for {@link UserInfo}.
 *
 * @author Kaz Nishimura
 */
final class UserInfoTest
{
    /**
     * {@link SessionUser} object for each test.
     */
    private SessionUser sessionUser = null;

    /**
     * Sets up each test.
     */
    @BeforeEach
    void setUp()
    {
        sessionUser = new SessionUser(new BitbucketClient());
    }

    /**
     * Tears down each test.
     */
    @AfterEach
    void tearDown()
    {
        sessionUser = null;
    }

    /**
     * Tests {@link UserInfo#getSessionUser()}.
     */
    @Test
    void testSessionUser1()
    {
        UserInfo userInfo = new UserInfo(sessionUser);
        assertEquals(sessionUser, userInfo.getSessionUser());
    }

    /**
     * Tests{@link UserInfo#getName()}.
     */
    @Test
    void testName1()
    {
        UserInfo userInfo = new UserInfo(sessionUser);
        userInfo.setName("");
        assertEquals("", userInfo.getName());
    }

    /**
     * Tests{@link UserInfo#getName()}.
     */
    @Test
    void testName2()
    {
        UserInfo userInfo = new UserInfo(sessionUser);
        userInfo.setName("name");
        assertEquals("name", userInfo.getName());
    }

    /**
     * Tests {@link UserInfo#lookUp lookUp}.
     */
    @Disabled("Faces runtime is required")
    @Test
    void testLookUp()
    {
        UserInfo userInfo = new UserInfo(sessionUser);
        Object outcome;

        userInfo.setName("");
        outcome = userInfo.lookUp();
        assertNull(outcome);
        assertNull(userInfo.getUser());
        assertEquals(false, userInfo.isFound());

        userInfo.setName("kazssym");
        outcome = userInfo.lookUp();
        assertNull(outcome);
        assertNotNull(userInfo.getUser());
        assertEquals(true, userInfo.isFound());
        assertEquals("kazssym", userInfo.getUser().getUsername());
    }
}
