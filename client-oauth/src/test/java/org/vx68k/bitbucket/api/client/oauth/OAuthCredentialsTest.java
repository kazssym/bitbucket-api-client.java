/*
 * OAuthCredentialsTest
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

package org.vx68k.bitbucket.api.client.oauth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for {@link OAuthCredentials}.
 * @author Kaz Nishimura
 * @since 4.0
 */
public class OAuthCredentialsTest {

    static final String ID = "id";

    static final String SECRET = "secret";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDefaultConstructor() {
        OAuthCredentials credentials = new OAuthCredentials();
        assertNull(credentials.getId());
        assertNull(credentials.getSecret());
    }

    @Test
    public void testConstructorWithIdAndSecret() {
        OAuthCredentials credentials = new OAuthCredentials(ID, SECRET);
        assertEquals(ID, credentials.getId());
        assertEquals(SECRET, credentials.getSecret());
    }

    @Test
    public void testSetId() {
        OAuthCredentials credentials = new OAuthCredentials();
        credentials.setId(ID);
        assertEquals(ID, credentials.getId());
    }

    @Test
    public void testSetSecret() {
        OAuthCredentials credentials = new OAuthCredentials();
        credentials.setSecret(SECRET);
        assertEquals(SECRET, credentials.getSecret());
    }

    @Test
    public void testEquals() {
        OAuthCredentials c1 = new OAuthCredentials();
        OAuthCredentials c1a = new OAuthCredentials();
        OAuthCredentials c2 = new OAuthCredentials(ID, SECRET);
        OAuthCredentials c2a = new OAuthCredentials(ID, SECRET);
        OAuthCredentials c3 = new OAuthCredentials(ID, null);
        OAuthCredentials c3a = new OAuthCredentials(ID, null);
        OAuthCredentials c4 = new OAuthCredentials(null, SECRET);
        OAuthCredentials c4a = new OAuthCredentials(null, SECRET);
        OAuthCredentials c5 = new OAuthCredentials(ID, "");
        OAuthCredentials c5a = new OAuthCredentials(ID, "");
        OAuthCredentials c6 = new OAuthCredentials("", SECRET);
        OAuthCredentials c6a = new OAuthCredentials("", SECRET);
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
        OAuthCredentials c1 = new OAuthCredentials();
        OAuthCredentials c1a = new OAuthCredentials();
        OAuthCredentials c2 = new OAuthCredentials(ID, SECRET);
        OAuthCredentials c2a = new OAuthCredentials(ID, SECRET);
        OAuthCredentials c3 = new OAuthCredentials(ID, null);
        OAuthCredentials c3a = new OAuthCredentials(ID, null);
        OAuthCredentials c4 = new OAuthCredentials(null, SECRET);
        OAuthCredentials c4a = new OAuthCredentials(null, SECRET);
        OAuthCredentials c5 = new OAuthCredentials(ID, "");
        OAuthCredentials c5a = new OAuthCredentials(ID, "");
        OAuthCredentials c6 = new OAuthCredentials("", SECRET);
        OAuthCredentials c6a = new OAuthCredentials("", SECRET);
        assertTrue(c1.hashCode() == c1a.hashCode());
        assertTrue(c2.hashCode() == c2a.hashCode());
        assertTrue(c3.hashCode() == c3a.hashCode());
        assertTrue(c4.hashCode() == c4a.hashCode());
        assertTrue(c5.hashCode() == c5a.hashCode());
        assertTrue(c6.hashCode() == c6a.hashCode());
    }
}
