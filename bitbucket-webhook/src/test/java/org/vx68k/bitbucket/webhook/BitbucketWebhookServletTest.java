/*
 * BitbucketWebhookServletTest.java - class BitbucketWebhookServletTest
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

package org.vx68k.bitbucket.webhook;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vx68k.bitbucket.stub.StubHttpServletRequest;
import org.vx68k.bitbucket.stub.StubHttpServletResponse;
import org.vx68k.bitbucket.stub.StubServletConfig;

/**
 * Unit tests for {@link BitbucketWebhookServlet}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketWebhookServletTest
{
    /**
     * {@link ServletConfig} object for testing.
     */
    private ServletConfig servletConfig;

    /**
     * Prepares the {@link ServletConfig} object for testing.
     */
    @Before
    public void setUp()
    {
        servletConfig = new StubServletConfig(null);
    }

    /**
     * Releases the {@link ServletConfig} object.
     */
    @After
    public void tearDown()
    {
        servletConfig = null;
    }

    /**
     * Tests {@link BitbucketWebhookServlet#init init} and {@link
     * BitbucketWebhookServlet#destroy destroy}.
     *
     * @throws ServletException if a servlet error occurred
     */
    @Test
    public void testLifecycle() throws ServletException
    {
        HttpServlet servlet = new BitbucketWebhookServlet(null);
        servlet.init(servletConfig);
        servlet.destroy();
    }

    /**
     * Tests responses for {@code GET} requests.
     *
     * @exception ServletException if a servlet error occurred
     * @exception IOException if an I/O error occurred
     */
    @Test
    public void testGet() throws ServletException, IOException
    {
        HttpServlet servlet = new BitbucketWebhookServlet(null);
        servlet.init(servletConfig);
        try {
            StubHttpServletRequest request = new StubHttpServletRequest(null);
            request.setMethod("GET");

            StubHttpServletResponse response = new StubHttpServletResponse();
            servlet.service(request, response);
            assertNotEquals(HttpServletResponse.SC_OK, response.getStatus());
        }
        finally {
            servlet.destroy();
        }
    }

    /**
     * Tests responses for {@code HEAD} requests.
     *
     * @exception ServletException if a servlet error occurred
     * @exception IOException if an I/O error occurred
     */
    @Test
    public void testHead() throws ServletException, IOException
    {
        HttpServlet servlet = new BitbucketWebhookServlet(null);
        servlet.init(servletConfig);
        try {
            StubHttpServletRequest request = new StubHttpServletRequest(null);
            request.setMethod("HEAD");

            StubHttpServletResponse response = new StubHttpServletResponse();
            servlet.service(request, response);
            assertNotEquals(HttpServletResponse.SC_OK, response.getStatus());
        }
        finally {
            servlet.destroy();
        }
    }
}
