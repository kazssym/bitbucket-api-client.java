/*
 * StubHttpServletRequest.java - class StubHttpServletRequest
 * Copyright (C) 2015-2018 Kaz Nishimura
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
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.vx68k.bitbucket.stub;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 * Stub implementation of {@link HttpServletRequest}.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class StubHttpServletRequest extends StubServletRequest
    implements HttpServletRequest
{
    /**
     * Request method.
     */
    private String method = "GET";

    /**
     * Path information.
     */
    private String pathInfo = null;

    /**
     * Query string.
     */
    private String queryString = null;

    /**
     * Constructs this object with a {@link ServletContext} object.
     * The input stream shall be set to empty.
     *
     * @param context {@link ServletContext} object
     */
    public StubHttpServletRequest(final ServletContext context)
    {
        super(context);
    }

    /**
     * Constructs this object with a {@link ServletContext} object and a {@link
     * ServletInputStream} object.
     *
     * @param context {@link ServletContext} object
     * @param stream {@link ServletInputStream} object
     */
    public StubHttpServletRequest(final ServletContext context,
        final ServletInputStream stream)
    {
        super(context, stream);
    }

    /**
     * Sets the request method of this request to a {@link String} value.
     * The given value will be returned by {@link #getMethod getMethod}.
     *
     * @param value {@link String} value
     */
    public final void setMethod(final String value)
    {
        method = value;
    }

    /**
     * Sets the path information of this request to a {@link String} value.
     * The given value will be returned by {@link #getPathInfo getPathInfo}.
     *
     * @param value {@link String} value
     */
    public final void setPathInfo(final String value)
    {
        pathInfo = value;
    }

    /**
     * Sets the query string of this request to a {@link String} value.
     * The given value will be returned by {@link #getQueryString
     * getQueryString}.
     *
     * @param value {@link String} value
     */
    public final void setQueryString(final String value)
    {
        queryString = value;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public String getAuthType()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public Cookie[] getCookies()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code -1}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code -1}
     */
    @Override
    public long getDateHeader(final String name)
    {
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public String getHeader(final String name)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public Enumeration<String> getHeaders(final String name)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public Enumeration<String> getHeaderNames()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code -1}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code -1}
     */
    @Override
    public int getIntHeader(final String name)
    {
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the value set by {@link #setMethod
     * setMethod}, which is {@code "GET"} by default.</p>
     *
     * @return the value set by {@link #setMethod setMethod}
     */
    @Override
    public final String getMethod()
    {
        return method;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the value set by {@link #setPathInfo
     * setPathInfo}, which is {@code null} by default.</p>
     *
     * @return the value set by {@link #setPathInfo setPathInfo}
     */
    @Override
    public final String getPathInfo()
    {
        return pathInfo;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public String getPathTranslated()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code ""}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code ""}
     */
    @Override
    public String getContextPath()
    {
        return "";
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the value set by {@link #setQueryString
     * setQueryString}, which is {@code null} by default.</p>
     *
     * @return the value set by {@link #setQueryString setQueryString}
     */
    @Override
    public final String getQueryString()
    {
        return queryString;
    }


    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public String getRemoteUser()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code false}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code false}
     */
    @Override
    public boolean isUserInRole(final String role)
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public Principal getUserPrincipal()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code null}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@code null}
     */
    @Override
    public String getRequestedSessionId()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRequestURI()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuffer getRequestURL()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServletPath()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpSession getSession(final boolean create)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpSession getSession()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeSessionId()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdValid()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdFromCookie()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdFromURL()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public final boolean isRequestedSessionIdFromUrl()
    {
        return isRequestedSessionIdFromURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authenticate(final HttpServletResponse response)
        throws IOException, ServletException
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(final String username, final String password)
        throws ServletException
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() throws ServletException
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Part getPart(final String name)
        throws IOException, ServletException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends HttpUpgradeHandler> T upgrade(
        final Class<T> handlerClass)
        throws IOException, ServletException
    {
        return null;
    }
}
