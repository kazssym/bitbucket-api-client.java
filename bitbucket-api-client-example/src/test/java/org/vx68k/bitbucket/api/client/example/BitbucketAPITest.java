/*
 * BitbucketAPITest.java - class BitbucketAPITest
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
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.oauth.OAuthClient;

/**
 * Unit tests for [@link BitbucketAPI}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketAPITest
{
    /**
     * Test value for the client identifier.
     */
    private static final String CLIENT_ID = "id";

    /**
     * Test value for the client secret.
     */
    private static final String CLIENT_SECRET = "secret";

    /**
     * Clears system properties.
     */
    @Before
    public void setUp()
    {
        System.clearProperty(Properties.BITBUCKET_OAUTH_CLIENT_ID);
        System.clearProperty(Properties.BITBUCKET_OAUTH_CLIENT_SECRET);
    }

//    /**
//     * Tests anonymously.
//     */
//    @Test
//    public void testGetBitbucketClient()
//    {
//        BitbucketAPI applicationConfig = new BitbucketAPI();
//        OAuthClient bitbucketClient = applicationConfig.getBitbucketClient();
//        String clientId = bitbucketClient.getClientId();
//        String clientSecret = bitbucketClient.getClientSecret();
//        assertNull(clientId);
//        assertNull(clientSecret);
//
//        // @todo Add a test case with system properties.
//    }

    /**
     * Tests with credentials.
     */
    @Test
    public void testGetBitbucketClientWithCredentials()
    {
        OAuthClient bitbucketClient1 = BitbucketAPI.getBitbucketClient(
                null, null);
        assertNull(bitbucketClient1.getClientId());
        assertNull(bitbucketClient1.getClientSecret());

        OAuthClient bitbucketClient2 = BitbucketAPI.getBitbucketClient(
                CLIENT_ID, CLIENT_SECRET);
        assertEquals(CLIENT_ID, bitbucketClient2.getClientId());
        assertEquals(CLIENT_SECRET, bitbucketClient2.getClientSecret());
    }
}
