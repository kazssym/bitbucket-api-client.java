/*
 * SessionUserTest
 * Copyright (C) 2015 Nishimura Software Studio
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.vx68k.bitbucket.api.client.example;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.Service;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for {@link SessionUser}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class SessionUserTest {

    private BitbucketAPI bitbucketAPI;

    @Before
    public void setUp() {
        bitbucketAPI = new BitbucketAPI();
    }

    @After
    public void tearDown() {
        bitbucketAPI = null;
    }

    @Test
    public void testConstructorWithApplicationConfig() {
        SessionUser user = new SessionUser(bitbucketAPI);
        assertEquals(bitbucketAPI, user.getBitbucketAPI());
    }

//    @Test
//    public void testGetBitbucketService() throws IOException {
//        SessionUser user = new SessionUser();
//        user.setApplicationConfig(applicationConfig);
//        Service bitbucketService = user.getBitbucketService();
//        assertNotNull(bitbucketService);
//        assertNull(bitbucketService.getCurrentUser());
//    }
}
