/*
 * CredentialsTest - unit tests for Credentials
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for {@link Credentials}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class CredentialsTest {

    static final String CLIENT_ID = "id";

    static final String CLIENT_SECRET = "secret";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDefaultConstructor() {
        Credentials credentials = new Credentials();
        assertNull(credentials.getId());
        assertNull(credentials.getSecret());
    }

    @Test
    public void testConstructorWithIdAndSecret() {
        Credentials credentials = new Credentials(CLIENT_ID, CLIENT_SECRET);
        assertEquals(CLIENT_ID, credentials.getId());
        assertEquals(CLIENT_SECRET, credentials.getSecret());
    }

    @Test
    public void testSetID() {
        Credentials credentials = new Credentials();
        credentials.setId(CLIENT_ID);
        assertEquals(CLIENT_ID, credentials.getId());
    }

    @Test
    public void testSetSecret() {
        Credentials credentials = new Credentials();
        credentials.setSecret(CLIENT_SECRET);
        assertEquals(CLIENT_SECRET, credentials.getSecret());
    }

    @Test
    public void testEquals() {
        Credentials c1 = new Credentials();
        Credentials c1a = new Credentials();
        Credentials c2 = new Credentials(CLIENT_ID, CLIENT_SECRET);
        Credentials c2a = new Credentials(CLIENT_ID, CLIENT_SECRET);
        Credentials c3 = new Credentials(CLIENT_ID, null);
        Credentials c3a = new Credentials(CLIENT_ID, null);
        Credentials c4 = new Credentials(null, CLIENT_SECRET);
        Credentials c4a = new Credentials(null, CLIENT_SECRET);
        Credentials c5 = new Credentials(CLIENT_ID, "");
        Credentials c5a = new Credentials(CLIENT_ID, "");
        Credentials c6 = new Credentials("", CLIENT_SECRET);
        Credentials c6a = new Credentials("", CLIENT_SECRET);
        assertTrue(c1.equals(c1a));
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertFalse(c1.equals(c5));
        assertFalse(c1.equals(c6));
        assertTrue(c2.equals(c2a));
        assertFalse(c2.equals(c3));
        assertFalse(c2.equals(c4));
        assertFalse(c2.equals(c5));
        assertFalse(c2.equals(c6));
        assertTrue(c3.equals(c3a));
        assertFalse(c3.equals(c4));
        assertFalse(c3.equals(c5));
        assertFalse(c3.equals(c6));
        assertTrue(c4.equals(c4a));
        assertFalse(c4.equals(c5));
        assertFalse(c4.equals(c6));
        assertTrue(c5.equals(c5a));
        assertFalse(c5.equals(c6));
        assertTrue(c6.equals(c6a));
    }

    @Test
    public void testHashCode() {
        Credentials c1 = new Credentials();
        Credentials c1a = new Credentials();
        Credentials c2 = new Credentials(CLIENT_ID, CLIENT_SECRET);
        Credentials c2a = new Credentials(CLIENT_ID, CLIENT_SECRET);
        Credentials c3 = new Credentials(CLIENT_ID, null);
        Credentials c3a = new Credentials(CLIENT_ID, null);
        Credentials c4 = new Credentials(null, CLIENT_SECRET);
        Credentials c4a = new Credentials(null, CLIENT_SECRET);
        Credentials c5 = new Credentials(CLIENT_ID, "");
        Credentials c5a = new Credentials(CLIENT_ID, "");
        Credentials c6 = new Credentials("", CLIENT_SECRET);
        Credentials c6a = new Credentials("", CLIENT_SECRET);
        assertTrue(c1.hashCode() == c1a.hashCode());
        assertTrue(c2.hashCode() == c2a.hashCode());
        assertTrue(c3.hashCode() == c3a.hashCode());
        assertTrue(c4.hashCode() == c4a.hashCode());
        assertTrue(c5.hashCode() == c5a.hashCode());
        assertTrue(c6.hashCode() == c6a.hashCode());
    }
}
