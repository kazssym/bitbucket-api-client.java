/*
 * OAuthRedirectionTest
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

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vx68k.bitbucket.stub.StubHttpServletRequest;
import org.vx68k.bitbucket.stub.StubHttpServletResponse;

/**
 * Collection of unit tests for {@link OAuthRedirection}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class OAuthRedirectionTest {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() {
        request = new StubHttpServletRequest(null);
        response = new StubHttpServletResponse(null);
    }

    @After
    public void tearDown() {
        response = null;
        request = null;
    }

    @Test
    public void testInitialize() {
        OAuthRedirection redirection =
                new OAuthRedirection(request, response);
        assertEquals(request, redirection.getRequest());
        assertEquals(response, redirection.getResponse());
    }
}
