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
public final class ClientAuthenticator implements ClientRequestFilter
{
    /**
     * Client identifier for OAuth.
     */
    private String clientId = null;

    /**
     * Client secret for OAuth.
     */
    private String clientSecret = null;

    /**
     * Access token.
     */
    private String accessToken = null;

    /**
     * Flag to indicate whether the client is to be authenticated or not.
     */
    private boolean toAuthenticateClient = false;

    /**
     * Sets the client identifier for OAuth.
     *
     * @param newValue a new value of the client identifier
     */
    public final void setClientId(final String newValue)
    {
        clientId = newValue;
    }

    /**
     * Sets the client secret for OAuth.
     *
     * @param newValue a new value of the client secret.
     */
    public final void setClientSecret(final String newValue)
    {
        clientSecret = newValue;
    }

    /**
     * Sets the access token.
     *
     * @param newValue a new value of the access token
     */
    public void setAccessToken(final String newValue)
    {
        accessToken = newValue;
        authenticateClient(false);
    }

    /**
     * Uses client authentication for requests.
     *
     * @param toAuthenticate {@code true} if client authentication is to be
     * used
     */
    public void authenticateClient(final boolean toAuthenticate)
    {
        toAuthenticateClient = toAuthenticate;
    }

    @Override
    public void filter(final ClientRequestContext requestContext)
        throws IOException
    {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();

        if (clientId != null && clientSecret != null) {
            if (toAuthenticateClient) {
                String credentials = Base64.getEncoder().encodeToString(
                    String.format("%s:%s", clientId, clientSecret).getBytes());
                headers.add("Authorization", "Basic " + credentials);
            }
            else if (accessToken != null) {
                headers.add("Authorization", "Bearer " + accessToken);
            }
        }
    }
}
