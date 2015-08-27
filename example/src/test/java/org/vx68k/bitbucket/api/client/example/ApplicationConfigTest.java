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
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Credentials;
import static org.junit.Assert.*;

/**
 *
 * @author Kaz Nishimura
 */
public class ApplicationConfigTest {

    private static final String envClientID =
            System.getenv("BITBUCKET_CLIENT_ID");
    private static final String envClientSecret =
            System.getenv("BITBUCKET_CLIENT_SECRET");

    @Before
    public void setUp() {
        System.clearProperty(Constants.BITBUCKET_CLIENT_ID_PROPERTY_NAME);
        System.clearProperty(Constants.BITBUCKET_CLIENT_SECRET_PROPERTY_NAME);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDefaultConstructor() {
        ApplicationConfig config = new ApplicationConfig();
        Credentials clientCredentials = config.getBitbucketClient()
                .getCredentials();
        if (envClientID != null && envClientSecret != null) {
            assertEquals(envClientID, clientCredentials.getId());
            assertEquals(envClientSecret, clientCredentials.getSecret());
        } else {
            assertNull(clientCredentials);
        }
        // TODO: Add a test case with system properties.
    }

    @Test
    public void testConstructorWithClient() {
        ApplicationConfig config = new ApplicationConfig(new Client());
        assertNull(config.getBitbucketClient().getCredentials());
    }
}