/*
 * WebhookServlet
 * Copyright (C) 2014-2015 Nishimura Software Studio
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
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes HTTP requests from Bitbucket webhooks.
 * @author Kaz Nishimura
 * @since 4.0
 */
@WebServlet(
        name = "Bitbucket Webhook Servlet",
        urlPatterns = {"/webhook/*"})
public class WebhookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private Event<RepositoryPush> repositoryPushEvent;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        JsonReader reader = Json.createReader(request.getInputStream());
        try {
            JsonObject object = reader.readObject();
            dispatch(object);
        } catch (JsonParsingException t) {
            log("JSON parsing error", t);
        }
        // TODO: Use HttpServletResponse.SC_OK instead.
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        // TODO: Return a result page.
        response.getWriter().close();
    }

    protected void dispatch(JsonObject jsonObject) {
        if (jsonObject.containsKey(WebhookJsonKeys.PUSH)) {
            repositoryPushEvent.fire(new RepositoryPush(jsonObject));
        } else {
            log("Unhandled JSON: " + jsonObject.toString());
        }
    }
}
