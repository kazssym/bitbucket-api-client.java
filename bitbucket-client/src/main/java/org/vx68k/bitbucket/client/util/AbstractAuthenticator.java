/*
 * AbstractAuthenticator.java
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

import java.io.Serializable;
import java.net.URI;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * Abstract authenticator class for the JAX-RS Client API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public abstract class AbstractAuthenticator
    implements ClientRequestFilter, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Base URI to which authenticated requests shall be sent.
     */
    private final URI base;

    /**
     * Constructs an authenticator.
     *
     * @param base the base URI
     */
    protected AbstractAuthenticator(final URI base)
    {
        if (base == null) {
            throw new IllegalArgumentException("Base URI is null");
        }
        else if (!(base.isAbsolute())) {
            throw new IllegalArgumentException("Base URI is not absolute");
        }

        this.base = base;
    }

    protected abstract void authenticate(final ClientRequestContext context);

    @Override
    public final void filter(final ClientRequestContext context)
    {
        URI uri = context.getUri();
        if (!(uri.equals(base.relativize(uri)))) {
            authenticate(context);
        }
    }
}
