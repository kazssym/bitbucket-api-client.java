/*
 * ClientAuthenticator.java
 * Copyright (C) 2018 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.vx68k.bitbucket.api.client.internal;

import java.io.IOException;
import java.util.Base64;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Authentication filter.
 *
 * @author Kaz Nishimura
 */
public class ClientAuthenticator implements ClientRequestFilter
{
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException
    {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();

        Object clientId = requestContext.getProperty("clientId");
        Object clientSecret = requestContext.getProperty("clientSecret");
        if (clientId != null && clientSecret != null) {
            String credentials = Base64.getEncoder().encodeToString(
                String.format("%s:%s", clientId, clientSecret).getBytes());
            headers.add("Authorization", "Basic " + credentials);
        }
    }
}
