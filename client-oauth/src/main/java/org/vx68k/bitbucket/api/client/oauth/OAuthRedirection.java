/*
 * OAuthRedirection
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OAuth redirection.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class OAuthRedirection {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    /**
     * Constructs a new object.
     *
     * @param request HTTP servlet request
     * @param response HTTP servlet response
     */
    public OAuthRedirection(
            HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Returns the HTTP servlet request.
     *
     * @return HTTP servlet request passed to the constructor
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Returns the HTTP servlet response.
     *
     * @return HTTP servlet response passed to the constructor
     */
    public HttpServletResponse getResponse() {
        return response;
    }
}
