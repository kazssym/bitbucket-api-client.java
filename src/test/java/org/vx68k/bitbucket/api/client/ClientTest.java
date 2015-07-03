/*
 * ClientTest - unit tests for Client
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
import org.vx68k.bitbucket.api.client.util.Credentials;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for [@link Client}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class ClientTest {

    static final String TEST_ID = "testID";
    static final String TEST_SECRET = "testSecret";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testClientCredentials() {
        Client client = new Client();
        Credentials clientCredentials = client.getCredentials();
        assertNotNull(clientCredentials);
    }

    @Test
    public void testSession() {
        Client client = new Client();
        Session bitbucket = client.getSession();
        assertNotNull(bitbucket);
    }
}
