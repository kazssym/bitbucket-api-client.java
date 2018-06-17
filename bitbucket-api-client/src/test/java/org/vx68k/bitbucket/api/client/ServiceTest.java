/*
 * ServiceTest
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

package org.vx68k.bitbucket.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Collection of unit tests for {@link Service}.
 *
 * @author Kaz Nishimura
 */
public class ServiceTest {

    /**
     * Undocumented.
     */
    private static final String USER_NAME = "kazssym";

    /**
     * Undocumented.
     */
    private static final UUID USER_UUID
            = UUID.fromString("cebb58cd-f699-4393-8762-e0f743ccf770");

    /**
     * Undocumented.
     */
    private Client client;

    /**
     * Undocumented.
     */
    @Before
    public void setUp() {
        client = new Client();
    }

    /**
     * Undocumented.
     */
    @After
    public void tearDown() {
        client = null;
    }

    /**
     * Undocumented.
     */
    @Test
    public void testIsAuthenticated() {
        Service service = client.getService();
        assertFalse(service.isAuthenticated());
    }

    /**
     * Undocumented.
     *
     * @throws IOException undocumented
     */
    @Test
    public void testGetCurrentUser() throws IOException {
        Service service = client.getService();
        BitbucketUser currentUser = service.getCurrentUser();
        assertNull(currentUser);
    }

    /**
     * Undocumented.
     *
     * @throws IOException undocumented
     */
    @Test @Ignore
    public void testGetUser() throws IOException {
        Service service = client.getService();
        BitbucketUser user1 = service.getUser(USER_NAME);
        BitbucketUser user2 = service.getUser(USER_UUID);
        assertNotNull(user1);
        assertNotNull(user2);
        assertEquals(USER_NAME, user1.getName());
        assertEquals(USER_UUID, user2.getUUID());
        assertTrue(user1.equals(user2));
    }
}
