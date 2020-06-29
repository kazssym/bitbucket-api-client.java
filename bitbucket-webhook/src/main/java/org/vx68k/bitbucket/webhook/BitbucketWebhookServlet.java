/*
 * BitbucketWebhookServlet.java - class BitbucketWebhookServlet
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

import java.io.BufferedReader;
import java.io.IOException;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes HTTP requests from Bitbucket webhooks.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class BitbucketWebhookServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * Media type for JSON streams.
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * Event to fire.
     */
    private Event<BitbucketWebhookEvent> event;

    private Jsonb jsonb;

    @Inject
    public final void setEvent(final Event<BitbucketWebhookEvent> event)
    {
        this.event = event;
    }

    @Override
    public void init() throws ServletException
    {
        jsonb = JsonbBuilder.create();
    }

    @Override
    public void destroy()
    {
        try {
            jsonb.close();
        }
        catch (final Exception e) {
            log("Unexpected exception", e);
        }
        jsonb = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void doPost(final HttpServletRequest request,
        final HttpServletResponse response) throws ServletException
    {
        try {
            if (request.getCharacterEncoding() == null) {
                request.setCharacterEncoding("UTF-8");
            }

            BufferedReader input = request.getReader();
            event.fire(jsonb.fromJson(input, BitbucketWebhookEvent.class));

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(APPLICATION_JSON);
            response.setCharacterEncoding("UTF-8");

            response.getWriter().print("{\"status\":\"OK\"}");
        }
        catch (final Exception e) {
            log("Exception thrown", e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            catch (final IOException e1) {
                log("Double exception thrown", e1);
            }
        }
    }
}
