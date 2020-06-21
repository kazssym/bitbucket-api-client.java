/*
 * BasicAuthenticator.java
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
import java.util.Base64;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Basic authenticator for the JAX-RS Client API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class BasicAuthenticator extends AbstractAuthenticator
{
    /**
     * Username.
     */
    private String username = null;

    /**
     * Password.
     */
    private String password = null;

    /**
     * Constructs a Basic authenticator.
     *
     * @param baseUri the base URI for which the authenticator attempt Basic
     * authentication.
     */
    public BasicAuthenticator(final URI baseUri)
    {
        super(baseUri);
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public final String getUsername()
    {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param newValue a new value of the username
     */
    public final void setUsername(final String newValue)
    {
        username = newValue;
    }

    /**
     * Sets the password.
     *
     * @param newValue a new value of the password
     */
    public final void setPassword(final String newValue)
    {
        password = newValue;
    }

    @Override
    protected final void authenticate(final ClientRequestContext context)
    {
        MultivaluedMap<String, Object> headers = context.getHeaders();
        if (username != null && password != null) {
            String credentials = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes());
            headers.add("Authorization", "Basic " + credentials);
        }
    }
}
