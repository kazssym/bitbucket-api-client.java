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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
public class StubHttpServletRequest implements HttpServletRequest
{
    /**
     * Default local host address.
     */
    private static final InetAddress LOCAL = InetAddress.getLoopbackAddress();

    /**
     * Default HTTP server port.
     */
    public static final int HTTP_PORT = 80;

    /**
     * Default HTTPS server port.
     */
    public static final int HTTPS_PORT = 443;

    /**
     * {@link ServletContext} object given to the constructor.
     */
    private final ServletContext servletContext;

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
     * Remote TCP port.
     */
    private int remotePort = 0;

    /**
     * Local host name.
     */
    private String localName = LOCAL.getHostName();

    /**
     * Local host IP address.
     */
    private String localAddr = LOCAL.getHostAddress();

    /**
     * Local TCP port.
     */
    private int localPort = HTTP_PORT;

    /**
     * Constructs this request with a {@link ServletContext} object.
     *
     * @param context {@link ServletContext} object
     */
    public StubHttpServletRequest(final ServletContext context)
    {
        servletContext = context;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAttribute(final String name)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getAttributeNames()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCharacterEncoding()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacterEncoding(final String env)
        throws UnsupportedEncodingException
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getContentLength()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentType()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParameter(final String name)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getParameterNames()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getParameterValues(final String name)
    {
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String[]> getParameterMap()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProtocol()
    {
        return "HTTP/1.1";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getScheme()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServerName()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getServerPort()
    {
        return 80;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedReader getReader() throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteAddr()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteHost()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getContentLengthLong()
    {
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation does nothing.</p>
     */
    @Override
    public final void setAttribute(final String name, final Object object)
    {
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation does nothing.</p>
     */
    @Override
    public final void removeAttribute(final String name)
    {
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the current locale.</p>
     *
     * @return the current locale
     */
    @Override
    public final Locale getLocale()
    {
        return Locale.getDefault();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns an empty enumeration.</p>
     *
     * @return an empty enumeration
     */
    @Override
    public final Enumeration<Locale> getLocales()
    {
        return Collections.<Locale>emptyEnumeration();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code false}.</p>
     *
     * @return {@code false}
     */
    @Override
    public final boolean isSecure()
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
    public RequestDispatcher getRequestDispatcher(final String path)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation uses {@link ServletContext#getRealPath}.</p>
     */
    @Override
    @Deprecated
    public final String getRealPath(final String path)
    {
        return servletContext.getRealPath(path);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the remote TCP port of this request.</p>
     *
     * @return the remote TCP port
     */
    @Override
    public final int getRemotePort()
    {
        return remotePort;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the local host name ({@code "localhost"}
     * by default) of this request.</p>
     *
     * @return the local host name
     */
    @Override
    public final String getLocalName()
    {
        return localName;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the local IP address ({@code
     * "127.0.0.1"} by default) of this request.</p>
     *
     * @return the local IP address
     */
    @Override
    public final String getLocalAddr()
    {
        return localAddr;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the local TCP port ({@value #HTTP_PORT}
     * by default) of this request.</p>
     *
     * @return the local TCP port
     */
    @Override
    public final int getLocalPort()
    {
        return localPort;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the {@link ServletContext} object given
     * to the constructor.</p>
     *
     * @return the {@link ServletContext} object
     */
    @Override
    public final ServletContext getServletContext()
    {
        return servletContext;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws {@link IllegalStateException}.</p>
     *
     * @exception IllegalStateException always
     */
    @Override
    public AsyncContext startAsync()
    {
        throw new IllegalStateException("Not supported");
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws {@link IllegalStateException}.</p>
     *
     * @exception IllegalStateException always
     */
    @Override
    public AsyncContext startAsync(final ServletRequest request,
        final ServletResponse response)
    {
        throw new IllegalStateException("Not supported");
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@code false}.</p>
     *
     * @return {@code false}
     */
    @Override
    public boolean isAsyncStarted()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns false.</p>
     *
     * @return {@code false}
     */
    @Override
    public boolean isAsyncSupported()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws {@link IllegalStateException}.</p>
     *
     * @exception IllegalStateException always
     */
    @Override
    public AsyncContext getAsyncContext()
    {
        throw new IllegalStateException("Not supported");
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns {@link DispatcherType#REQUEST}.
     * Subclasses may override this method to return other values.</p>
     *
     * @return {@link DispatcherType#REQUEST}
     */
    @Override
    public DispatcherType getDispatcherType()
    {
        return DispatcherType.REQUEST;
    }
}
