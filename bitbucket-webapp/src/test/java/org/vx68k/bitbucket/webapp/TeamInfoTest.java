/*
 * TeamInfoTest.jar - class TeamInfoTest
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * Unit tests for {@link TeamInfo}.
 *
 * @author Kaz Nishimura
 */
final class TeamInfoTest
{
    /**
     * {@link SessionUser} object for each test.
     */
    private SessionUser sessionUser;

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
     * Tests {@link TeamInfo#getSessionUser()}.
     */
    @Test
    void testSessionUser1()
    {
        TeamInfo teamInfo = new TeamInfo(sessionUser);
        assertEquals(sessionUser, teamInfo.getSessionUser());
    }

    /**
     * Tests{@link TeamInfo#getName()}.
     */
    @Test
    void testName1()
    {
        TeamInfo teamInfo = new TeamInfo(sessionUser);
        teamInfo.setName("");
        assertEquals("", teamInfo.getName());
    }

    /**
     * Tests{@link TeamInfo#getName()}.
     */
    @Test
    void testName2()
    {
        TeamInfo teamInfo = new TeamInfo(sessionUser);
        teamInfo.setName("name");
        assertEquals("name", teamInfo.getName());
    }

    /**
     * Tests {@link TeamInfo#lookUp lookUp}.
     */
    @Disabled("Faces runtime is required")
    @Test
    void testLookUp()
    {
        TeamInfo teamInfo = new TeamInfo(sessionUser);
        Object outcome;

        teamInfo.setName("");
        outcome = teamInfo.lookUp();
        assertNull(outcome);
        assertNull(teamInfo.getTeam());
        assertEquals(false, teamInfo.isFound());

        teamInfo.setName("vx68k");
        outcome = teamInfo.lookUp();
        assertNull(outcome);
        assertNotNull(teamInfo.getTeam());
        assertEquals(true, teamInfo.isFound());
        assertEquals("kazssym", teamInfo.getTeam().getUsername());
    }
}
