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
 * Unit tests for {@link TeamInfo}.
 *
 * @author Kaz Nishimura
 */
public final class TeamInfoTest
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
     * Tests {@link TeamInfo#getUserContext getUserContext}.
     */
    @Test
    public void testGetUserContext()
    {
        TeamInfo teamInfo = new TeamInfo(userContext);
        assertEquals(userContext, teamInfo.getUserContext());
    }

    /**
     * Tests{@link TeamInfo#getName getName}.
     */
    @Test
    public void testGetName()
    {
        TeamInfo teamInfo = new TeamInfo(userContext);

        teamInfo.setName("");
        assertEquals("", teamInfo.getName());

        teamInfo.setName("user");
        assertEquals("user", teamInfo.getName());
    }

    /**
     * Tests {@link TeamInfo#lookUp lookUp}.
     */
    @Ignore("Faces runtime is required")
    @Test
    public void testLookUp()
    {
        TeamInfo teamInfo = new TeamInfo(userContext);
        Object outcome;

        teamInfo.setName("");
        outcome = teamInfo.lookUp();
        assertNull(outcome);
        assertNull(teamInfo.getTeam());
        assertFalse(teamInfo.isFound());

        teamInfo.setName("vx68k");
        outcome = teamInfo.lookUp();
        assertNull(outcome);
        assertNotNull(teamInfo.getTeam());
        assertTrue(teamInfo.isFound());
        assertEquals("kazssym", teamInfo.getTeam().getName());
    }
}
