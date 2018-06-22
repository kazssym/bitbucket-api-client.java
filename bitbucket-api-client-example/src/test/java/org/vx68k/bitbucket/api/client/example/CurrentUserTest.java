/*
 * CurrentUserTest.java - class CurrentUserTest
 * Copyright (C) 2015-2018 Kaz Nishimura
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CurrentUser}.
 *
 * @author Kaz Nishimura
 */
public final class CurrentUserTest
{
    /**
     * {@link BitbucketAPI} object.
     */
    private BitbucketAPI bitbucketAPI;

    /**
     * Prepares for a test run.
     */
    @Before
    public void setUp()
    {
        bitbucketAPI = new BitbucketAPI();
    }

    /**
     * Releases the resources used by a test run.
     */
    @After
    public void tearDown()
    {
        bitbucketAPI = null;
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testBitbucketAPI()
    {
        CurrentUser user = new CurrentUser(bitbucketAPI);
        assertEquals(bitbucketAPI, user.getBitbucketAPI());
    }

//    @Test
//    public void testGetBitbucketService() throws IOException {
//        CurrentUser user = new CurrentUser();
//        user.setApplicationConfig(applicationConfig);
//        Service bitbucketService = user.getBitbucketService();
//        assertNotNull(bitbucketService);
//        assertNull(bitbucketService.getCurrentUser());
//    }
}
