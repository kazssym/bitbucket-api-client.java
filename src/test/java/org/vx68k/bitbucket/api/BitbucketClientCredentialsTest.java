/*
 * BitbucketClientCredentialsTest - test for BitbucketClientCredentials
 * Copyright (C) 2015 Kaz
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

package org.vx68k.bitbucket.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for {@link BitbucketClientCredentials}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketClientCredentialsTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testID() {
        BitbucketClientCredentials object = new BitbucketClientCredentials();
        assertNotNull(object.getID());
        assertEquals("", object.getID());

        final String TEST_ID = "testID";
        object.setID(TEST_ID);
        assertEquals(TEST_ID, object.getID());
    }

    @Test
    public void testSecret() {
        BitbucketClientCredentials object = new BitbucketClientCredentials();
        assertNotNull(object.getSecret());
        assertEquals("", object.getSecret());

        final String TEST_SECRET = "testSecret";
        object.setID(TEST_SECRET);
        assertEquals(TEST_SECRET, object.getID());
    }
}
