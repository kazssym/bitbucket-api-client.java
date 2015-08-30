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

import java.io.IOException;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for {@link Service}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class ServiceTest {

    private static final String USER_USERNAME = "kazssym";

    private static final UUID USER_UUID
            = UUID.fromString("cebb58cd-f699-4393-8762-e0f743ccf770");

    private Client client;

    @Before
    public void setUp() {
        client = new Client();
    }

    @After
    public void tearDown() {
        client = null;
    }

    @Test
    public void testIsAuthenticated() {
        Service service = client.getService();
        assertFalse(service.isAuthenticated());
    }

    @Test
    public void testGetCurrentUser() throws IOException {
        Service service = client.getService();
        User currentUser = service.getCurrentUser();
        assertNull(currentUser);
    }

    @Test
    public void testGetUser() throws IOException {
        Service service = client.getService();
        User user1 = service.getUser(USER_USERNAME);
        User user2 = service.getUser(USER_UUID);
        assertNotNull(user1);
        assertNotNull(user2);
        assertEquals(USER_USERNAME, user1.getUsername());
        assertEquals(USER_UUID, user2.getUuid());
        assertTrue(user1.equals(user2));
    }
}
