/*
 * OAuthRedirectionServlet
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
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for an OAuth redirection endpoint.
 * Applications can observe a redirection as {@link OAuthRedirection} by CDI
 * events.
 * The default URL pattern is <code>/authorized/*</code> unless it is
 * explicitly specified in the web application deployment descriptor.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@WebServlet(
        name = "OAuth Redirection Endpoint Servlet",
        urlPatterns = {"/authorized/*"})
public class OAuthRedirectionServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Inject
    private Event<OAuthRedirection> redirectionEvent;

    @Override
    protected void doGet(final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException
    {
        // Note: CDI events are synchronous.
        redirectionEvent.fire(new OAuthRedirection(request, response));
        if (!response.isCommitted()) {
            // If nothing has committed the response, the current redirection
            // is forbidden.
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
