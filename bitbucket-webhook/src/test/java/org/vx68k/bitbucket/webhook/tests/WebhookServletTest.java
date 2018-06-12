/*
 * WebhookServetTest.java - class WebhookServletTest
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

package org.vx68k.bitbucket.webhook.tests;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.vx68k.bitbucket.stub.StubHttpServletRequest;
import org.vx68k.bitbucket.stub.StubHttpServletResponse;
import org.vx68k.bitbucket.webhook.WebhookServlet;

/**
 * Unit tests for {@link WebhookServlet}.
 *
 * @author Kaz Nishimura
 * @since 4.0
 */
public final class WebhookServletTest implements ServletConfig
{
    /**
     * Tests {@link WebhookServlet#init init} and {@link
     * WebhookServlet#destroy destroy}.
     *
     * @throws ServletException if a servlet error occurred
     */
    @Test
    public void testLifecycle() throws ServletException
    {
        WebhookServlet servlet = new WebhookServlet(null);
        servlet.init(this);
        servlet.destroy();
    }

    /**
     * Tests responses for {@code GET} requests.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testGet() throws ServletException, IOException
    {
        WebhookServlet servlet = new WebhookServlet(null);
        servlet.init(this);
        try {
            StubHttpServletRequest request = new StubHttpServletRequest(null);
            request.setMethod("GET");

            StubHttpServletResponse response = new StubHttpServletResponse();
            servlet.service(request, response);
            assertNotEquals(HttpServletResponse.SC_OK, response.getStatus());
        } finally {
            servlet.destroy();
        }
    }

    /**
     * Tests responses for {@code HEAD} requests.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testHead() throws ServletException, IOException
    {
        WebhookServlet servlet = new WebhookServlet(null);
        servlet.init(this);
        try {
            StubHttpServletRequest request = new StubHttpServletRequest(null);
            request.setMethod("HEAD");

            StubHttpServletResponse response = new StubHttpServletResponse();
            servlet.service(request, response);
            assertNotEquals(HttpServletResponse.SC_OK, response.getStatus());
        } finally {
            servlet.destroy();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServletName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInitParameter(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
