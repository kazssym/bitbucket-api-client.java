/*
 * OAuthFilter.java - class OAuthFilter
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client.example;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter to process OAuth redirection.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@WebFilter(urlPatterns = {"/*"})
public class OAuthFilter implements Filter, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * {@link CurrentUser} object given to the constructor.
     */
    private final CurrentUser currentUser;

    /**
     * {@link FilterConfig} object given to {@link #init init}.
     */
    private transient FilterConfig filterConfig = null;

    /**
     * Constructs this object.
     *
     * @param user {@link CurrentUser} object to authenticate.
     */
    @Inject
    public OAuthFilter(final CurrentUser user)
    {
        currentUser = user;
    }

    /**
     * Returns the {@link FilterConfig} object given to {@link #init init}.
     *
     * @return the {@link FilterConfig} object given to {@link #init init}
     */
    public final FilterConfig getFilterConfig()
    {
        return filterConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final FilterConfig config) throws ServletException
    {
        filterConfig = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy()
    {
        filterConfig = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(final ServletRequest request,
        final ServletResponse response, final FilterChain chain)
        throws ServletException, IOException
    {
        // @todo Process authentication.
        chain.doFilter(request, response);
    }
}
