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

import java.io.IOException;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.stream.JsonParsingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes HTTP requests from Bitbucket webhooks.
 *
 * @author Kaz Nishimura
 * @since 4.0
 */
public class BitbucketWebhookServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * Media type for JSON streams.
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * Character encoding of UTF-8.
     */
    private static final String UTF8 = "UTF-8";

    /**
     * Event to fire.
     */
    private final Event<BitbucketWebhookEvent> bitbucketEvent;

    /**
     * Constructs this object with an {@link Event} object.
     *
     * @param event {@link Event} object
     */
    @Inject
    public BitbucketWebhookServlet(final Event<BitbucketWebhookEvent> event)
    {
        bitbucketEvent = event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void doPost(final HttpServletRequest request,
        final HttpServletResponse response) throws IOException
    {
        String contentType = request.getContentType();
        // Content types may be followed by parameters.
        if (contentType != null && contentType.contains(";")) {
            contentType = contentType.substring(0, contentType.indexOf(";"))
                .trim();
        }
        if (!APPLICATION_JSON.equalsIgnoreCase(contentType)) {
            log("Unexpected content type: " + contentType);
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        // Default encoding for JSON streams is UTF-8.
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(UTF8);
        }
        try (JsonReader reader = Json.createReader(request.getReader())) {
            JsonObject object = reader.readObject();
            // bitbucketEvent.fire(new WebhookEvent());
        }
        catch (JsonParsingException exception) {
            log("JSON parsing error", exception);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF8);
        try (JsonWriter writer = Json.createWriter(response.getWriter())) {
            JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("status", "OK");
            writer.write(builder.build());
        }
    }
}
