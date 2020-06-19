/*
 * BearerAuthenticator.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client.util;

import java.net.URI;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedMap;

public class BearerAuthenticator extends AbstractAuthenticator
{
    /**
     * Access token.
     */
    private String accessToken;

    BearerAuthenticator(final URI baseUri)
    {
        super(baseUri);
    }

    /**
     * Returns the access token.
     *
     * @return the access token
     */
    public final String getAccessToken()
    {
        return accessToken;
    }

    /**
     * Sets the access token to a string.
     *
     * @param accessToken a string for the access token
     */
    public final void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Validate the access token if necessary.
     * <p>This implementation does nothing.  A subclass should override this
     * method for its own validation method.</p>
     */
    protected void validateAccessToken()
    {
        // Nothing to do.
    }

    /**
     * Adds an {@code Authorization} HTTP header to a request when
     * authentication is required.
     * The header shall be added only if the request URI is below the base URI.
     *
     * @param context a client request context
     */
    @Override
    protected final void authenticate(final ClientRequestContext context)
    {
        if (accessToken != null) {
            validateAccessToken();

            MultivaluedMap<String, Object> headers = context.getHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
        }
    }
}
