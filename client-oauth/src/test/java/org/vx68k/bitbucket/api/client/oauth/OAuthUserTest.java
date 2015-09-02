/*
 * OAuthUserTest
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

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.Client;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for {@link OAuthUser}.
 * @author Kaz Nishimura
 * @since 3.0
 */
public class OAuthUserTest {

    private Client bitbucketClient;

    @Before
    public void setUp() {
        bitbucketClient = new Client();
    }

    @After
    public void tearDown() {
        bitbucketClient = null;
    }

    @Test
    public void testDefaultConstructor() {
        OAuthUser user = new OAuthUser();
        assertNull(user.getBitbucketClient());
    }

    @Test
    public void testConstructorWithClient() throws IOException {
        OAuthUser user = new OAuthUser(bitbucketClient);
        assertEquals(bitbucketClient, user.getBitbucketClient());
    }

    @Test
    public void testSetBitbucketClient() throws IOException {
        OAuthUser user = new OAuthUser();
        user.setBitbucketClient(bitbucketClient);
        assertEquals(bitbucketClient, user.getBitbucketClient());
    }

    // TODO: Add more tests.
}