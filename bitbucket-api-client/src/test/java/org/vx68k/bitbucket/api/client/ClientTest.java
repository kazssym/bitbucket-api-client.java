/*
 * ClientTest
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

import java.io.IOException;
import com.google.api.client.http.HttpResponseException;
import org.junit.Test;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Collection of unit tests for [@link Client}.
 *
 * @author Kaz Nishimura
 */
public class ClientTest {

    /**
     * Undocumented.
     */
    private static final String USER = "user";

    /**
     * Undocumented.
     */
    private static final String PASSWORD = "password";

    /**
     * Undocumented.
     */
    @Test
    public void testDefaultConstructor() {
        Client client = new Client();
        assertNull(client.getUser());
        assertNull(client.getPassword());
    }

    /**
     * Undocumented.
     */
    @Test
    public void testConstructorWithUserAndPassword() {
        Client client = new Client(USER, PASSWORD);
        assertEquals(USER, client.getUser());
        assertEquals(PASSWORD, client.getPassword());
    }

    /**
     * Undocumented.
     */
    @Test
    public void testSetUser() {
        Client client = new Client();
        client.setUser(USER);
        assertEquals(USER, client.getUser());
    }

    /**
     * Undocumented.
     */
    @Test
    public void testSetPassword() {
        Client client = new Client();
        client.setPassword(PASSWORD);
        assertEquals(PASSWORD, client.getPassword());
    }

    /**
     * Undocumented.
     *
     * @exception IOException undocumented
     */
    @Test
    public void testGetService() throws IOException {
        Client client = new Client();
        Service service1 = client.getService();
        assertNotNull(service1);
        assertFalse(service1.isAuthenticated());
        assertNull(service1.getCurrentUser());

        client.setUser(USER);
        client.setPassword(PASSWORD);
        Service service2 = client.getService();
        assertNotNull(service2);
        assertFalse(service2.isAuthenticated());
        try {
            BitbucketUser currentUser = service2.getCurrentUser();
            assertNotNull(currentUser);
        } catch (HttpResponseException exception) {
            // Expected.
            assertEquals(401, exception.getStatusCode());
        }
    }
}
