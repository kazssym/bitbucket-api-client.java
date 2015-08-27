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

    static final String TEST_ID = "testID";
    static final String TEST_SECRET = "testSecret";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDefault() {
        Credentials credentials = new Credentials();
        assertNull(credentials.getId());
        assertNull(credentials.getSecret());
    }

    @Test
    public void testInitialized() {
        Credentials credentials = new Credentials(TEST_ID, TEST_SECRET);
        assertEquals(TEST_ID, credentials.getId());
        assertEquals(TEST_SECRET, credentials.getSecret());
    }

    @Test
    public void testSetID() {
        Credentials credentials = new Credentials();
        credentials.setId(TEST_ID);
        assertEquals(TEST_ID, credentials.getId());
    }

    @Test
    public void testSetSecret() {
        Credentials credentials = new Credentials();
        credentials.setSecret(TEST_SECRET);
        assertEquals(TEST_SECRET, credentials.getSecret());
    }
}
