/*
 * BasicAuthenticator.java
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

package org.vx68k.bitbucket.client.util;

import java.io.IOException;
import java.util.Base64;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Basic authenticator.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public final class BasicAuthenticator implements ClientRequestFilter
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
     * Initializes the object.
     */
    public BasicAuthenticator()
    {
        // Nothing to do.
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param newValue a new value of the username
     */
    public void setUsername(final String newValue)
    {
        username = newValue;
    }

    /**
     * Sets the password.
     *
     * @param newValue a new value of the password
     */
    public void setPassword(final String newValue)
    {
        password = newValue;
    }

    @Override
    public void filter(final ClientRequestContext requestContext)
        throws IOException
    {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();

        if (username != null && password != null) {
            String credentials = Base64.getEncoder().encodeToString(
                String.format("%s:%s", username, password).getBytes());
            headers.add("Authorization", "Basic " + credentials);
        }
    }
}
