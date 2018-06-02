/*
 * BitbucketWebhookServetTest
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

package org.vx68k.bitbucket.webhook;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.vx68k.bitbucket.webhook.stub.StubHttpServletRequest;
import org.vx68k.bitbucket.webhook.stub.StubHttpServletResponse;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link WebhookServlet}.
 * @author Kaz Nishimura
 * @since 4.0
 */
public class WebhookServletTest implements ServletConfig {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test @Ignore
    public void testLifecycle() throws ServletException {
        WebhookServlet servlet = new WebhookServlet();
        servlet.init(this);
        servlet.destroy();
    }

    @Test @Ignore
    public void testGet() throws ServletException, IOException {
        WebhookServlet servlet = new WebhookServlet();
        servlet.init(this);
        try {
            StubHttpServletRequest request = new StubHttpServletRequest();
            request.setMethod("GET");

            StubHttpServletResponse response = new StubHttpServletResponse();
            servlet.service(request, response);
            assertNotEquals(HttpServletResponse.SC_OK, response.getStatus());
        } finally {
            servlet.destroy();
        }
    }

    @Test @Ignore
    public void testHead() throws ServletException, IOException {
        WebhookServlet servlet = new WebhookServlet();
        servlet.init(this);
        try {
            StubHttpServletRequest request = new StubHttpServletRequest();
            request.setMethod("HEAD");

            StubHttpServletResponse response = new StubHttpServletResponse();
            servlet.service(request, response);
            assertNotEquals(HttpServletResponse.SC_OK, response.getStatus());
        } finally {
            servlet.destroy();
        }
    }

    @Override
    public String getServletName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getInitParameter(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
