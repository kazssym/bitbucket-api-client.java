/*
 * OAuthLoginFilter.java
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

package org.vx68k.bitbucket.webapp;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter to process OAuth redirection.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
@WebFilter(urlPatterns = {"/*"})
public final class OAuth2LoginFilter implements Filter, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Session user object.
     */
    private SessionUser sessionUser;

    /**
     * {@link FilterConfig} object given to {@link #init(FilterConfig)}.
     */
    private transient FilterConfig filterConfig = null;

    /**
     * Returns the session user object.
     *
     * @return the session user object
     */
    public final SessionUser getSessionUser()
    {
        return sessionUser;
    }

    /**
     * Sets the session user object.
     *
     * @param sessionUser a session user object
     */
    @Inject
    public final void setSessionUser(final SessionUser sessionUser)
    {
        this.sessionUser = sessionUser;
    }

    /**
     * Returns the {@link FilterConfig} object given to {@link #init init}.
     *
     * @return the {@link FilterConfig} object given to {@link #init init}
     */
    public FilterConfig getFilterConfig()
    {
        return filterConfig;
    }

    /**
     * Returns the servlet context.
     *
     * @return the servlet context
     */
    public ServletContext getServletContext()
    {
        return filterConfig.getServletContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
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
        ServletContext servletContext = getServletContext();

        String state = request.getParameter("state");
        String code = request.getParameter("code");
        if (code != null) {
            servletContext.log("code = " + code);
            sessionUser.login(code, state);
        }
        else {
            String error = request.getParameter("error");
            if (error != null) {
                String errorDescription =
                    request.getParameter("error_description");
                servletContext.log("error = " + error);
                sessionUser.abortLogin(errorDescription, state);
            }
        }

        chain.doFilter(request, response);
    }
}
