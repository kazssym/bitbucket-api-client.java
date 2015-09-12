/*
 * ApplicationConfigTest
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.oauth.OAuthClient;
import org.vx68k.bitbucket.api.client.oauth.OAuthCredentials;
import static org.junit.Assert.*;

/**
 * Collections of unit tests for [@link ApplicationConfig}.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class ApplicationConfigTest {

    private static final String envClientID =
            System.getenv("BITBUCKET_OAUTH_CLIENT_ID");

    private static final String envClientSecret =
            System.getenv("BITBUCKET_OAUTH_CLIENT_SECRET");

    private static final String CLIENT_ID = "id";

    private static final String CLIENT_SECRET = "secret";

    @Before
    public void setUp() {
        System.clearProperty(Properties.BITBUCKET_OAUTH_CLIENT_ID);
        System.clearProperty(Properties.BITBUCKET_OAUTH_CLIENT_SECRET);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetBitbucketClient() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        OAuthClient bitbucketClient = applicationConfig.getBitbucketClient();
        OAuthCredentials clientCredentials = bitbucketClient.getCredentials();
        if (envClientID != null && envClientSecret != null) {
            assertNotNull(clientCredentials);
            assertEquals(envClientID, clientCredentials.getId());
            assertEquals(envClientSecret, clientCredentials.getSecret());
        } else {
            assertNull(clientCredentials);
        }

        // TODO: Add a test case with system properties.
    }

    @Test
    public void testGetBitbucketClientWithCredentials() {
        OAuthClient bitbucketClient1 = ApplicationConfig.getBitbucketClient(
                null);
        assertNull(bitbucketClient1.getCredentials());

        OAuthClient bitbucketClient2 = ApplicationConfig.getBitbucketClient(
                new OAuthCredentials(CLIENT_ID, CLIENT_SECRET));
        OAuthCredentials clientCredentials2 =
                bitbucketClient2.getCredentials();
        assertNotNull(clientCredentials2);
        assertEquals(CLIENT_ID, clientCredentials2.getId());
        assertEquals(CLIENT_SECRET, clientCredentials2.getSecret());
    }
}
