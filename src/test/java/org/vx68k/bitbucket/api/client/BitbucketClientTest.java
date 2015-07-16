/*
 * BitbucketClientTest
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

import java.io.IOException;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.http.HttpExecuteInterceptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for [@link BitbucketClient}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketClientTest {

    private static final String CLIENT_ID = "9AmnPtR344BRPQx35N";
    private static final String CLIENT_SECRET
            = "9zpV93JzpYfBVHDvsspsWKAZRs3bdavN";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testClientCredentials() {
        BitbucketClient client = new BitbucketClient();
        Credentials clientCredentials = client.getCredentials();
        assertNotNull(clientCredentials);
        assertTrue(clientCredentials.isEmpty());
    }

    @Test
    public void testAuthorizationFlow() throws IOException {
        BitbucketClient client = new BitbucketClient();

        HttpExecuteInterceptor authentication = client.getClientParameters();
        AuthorizationCodeFlow flow
                = client.getAuthorizationCodeFlow(authentication);
        assertNull(flow);

        client.getCredentials().setID(CLIENT_ID);
        client.getCredentials().setSecret(CLIENT_SECRET);
        authentication = client.getClientParameters();
        flow = client.getAuthorizationCodeFlow(authentication);
        assertNotNull(flow);

        String authorizationURL = flow.newAuthorizationUrl().build();
        assertNotNull(authorizationURL);
    }

    @Test
    public void testSession() {
        BitbucketClient client = new BitbucketClient();
        Session bitbucket = client.getSession();
        assertNotNull(bitbucket);
    }
}
