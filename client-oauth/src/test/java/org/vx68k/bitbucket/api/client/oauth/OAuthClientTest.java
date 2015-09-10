/*
 * OAuthClientTest
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
import java.net.URI;
import java.net.URISyntaxException;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for [@link OAuthClient}.
 * @author Kaz Nishimura
 * @since 4.0
 */
public class OAuthClientTest {

    private static final String CLIENT_ID = "9AmnPtR344BRPQx35N";

    private static final String CLIENT_SECRET =
            "9zpV93JzpYfBVHDvsspsWKAZRs3bdavN";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDefaultConstructor() {
        OAuthClient client = new OAuthClient();
        assertNull(client.getCredentials());
    }

    @Test
    public void testConstructorWithCredentials() {
        OAuthClient client = new OAuthClient(
                new OAuthCredentials(CLIENT_ID, CLIENT_SECRET));
        OAuthCredentials clientCredentials = client.getCredentials();
        assertNotNull(clientCredentials);
        assertEquals(CLIENT_ID, clientCredentials.getId());
        assertEquals(CLIENT_SECRET, clientCredentials.getSecret());
    }

    @Test
    public void testSetCredentials() {
        OAuthClient client = new OAuthClient();
        client.setCredentials(new OAuthCredentials(CLIENT_ID, CLIENT_SECRET));
        
        OAuthCredentials clientCredentials = client.getCredentials();
        assertNotNull(clientCredentials);
        assertEquals(CLIENT_ID, clientCredentials.getId());
        assertEquals(CLIENT_SECRET, clientCredentials.getSecret());
    }

    @Test
    public void testGetAuthorizationEndpoint() throws URISyntaxException {
        OAuthClient client = new OAuthClient();
        try {
            URI authorizationEndpoint = client.getAuthorizationEndpoint(
                    null, null);
            assertNull(authorizationEndpoint);
        } catch (NullPointerException exception) {
            // Expected.
        }

        client.setCredentials(new OAuthCredentials(CLIENT_ID, CLIENT_SECRET));
        URI authorizationEndpoint = client.getAuthorizationEndpoint(
                null, null);
        assertNotNull(authorizationEndpoint);

        // TODO: Add more tests.
    }

    @Ignore
    @Test
    public void testAuthorizationCodeFlow() throws IOException {
        OAuthClient client = new OAuthClient();

        AuthorizationCodeFlow flow1 = client.getAuthorizationCodeFlow(false);
        assertNull(flow1);

        AuthorizationCodeFlow flow2 = client.getAuthorizationCodeFlow(true);
        assertNull(flow2);

        client.setCredentials(new OAuthCredentials(CLIENT_ID, CLIENT_SECRET));

        AuthorizationCodeFlow flow3 = client.getAuthorizationCodeFlow(false);
        assertNotNull(flow3);

        String authorizationURL = flow3.newAuthorizationUrl().build();
        assertNotNull(authorizationURL);

        AuthorizationCodeFlow flow4 = client.getAuthorizationCodeFlow(true);
        assertNotNull(flow4);
    }
}