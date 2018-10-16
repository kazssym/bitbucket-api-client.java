/*
 * OAuth2Authenticator.java
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
import java.net.URI;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * OAuth 2.0 authenticator.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public final class OAuth2Authenticator implements ClientRequestFilter
{
    /**
     * URI prefix to which authenticated requests shall be sent.
     */
    private final String uriPrefix;

    /**
     * Access token.
     */
    private String accessToken = null;

    /**
     * Initializes the object.
     *
     * @param uriPrefix a value of the URI prefix (case-sensitive)
     */
    public OAuth2Authenticator(final String uriPrefix)
    {
        this.uriPrefix = uriPrefix;

        if (this.uriPrefix == null) {
            throw new IllegalArgumentException("URI prefix is null");
        }
    }

    /**
     * Returns the access token.
     *
     * @return the access token
     */
    public String getAccessToken()
    {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param newValue a new value of the access token
     */
    public void setAccessToken(final String newValue)
    {
        accessToken = newValue;
    }

    @Override
    public void filter(final ClientRequestContext requestContext)
        throws IOException
    {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        URI uri = requestContext.getUri();

        if (accessToken != null && uri.toString().startsWith(uriPrefix)) {
            headers.add("Authorization", "Bearer " + accessToken);
        }
    }
}
