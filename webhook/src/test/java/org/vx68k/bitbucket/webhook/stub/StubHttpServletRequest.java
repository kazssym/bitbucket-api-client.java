/*
 * StubHttpServletRequest
 * Copyright (C) 2015-2018 Nishimura Software Studio
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

package org.vx68k.bitbucket.webhook.stub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.Principal;
import java.util.Collection;
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
 * @since 1.0
 */
public class StubHttpServletRequest implements HttpServletRequest
{
    /**
     * Default local host information.
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
    private ServletContext servletContext;

    /**
     * Remote TCP port.
     */
    private int remotePort = 0;

    /**
     * Local host name.
     */
    private String localName = LOCAL.getHostName();

    /**
     * Local IP address.
     */
    private String localAddr = LOCAL.getHostAddress();

    /**
     * Local TCP port.
     */
    private int localPort = HTTP_PORT;

    /**
     * Request method.
     */
    private String method = "GET";

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
     *
     * @param value {@link String} value for the request method
     */
    public final void setMethod(final String value)
    {
        method = value;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(final String name)
    {
        return -1;
    }

    @Override
    public String getHeader(final String name)
    {
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(final String name)
    {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(final String name)
    {
        return 0;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(final String role)
    {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(final boolean create)
    {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId()
    {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(final HttpServletResponse response)
        throws IOException, ServletException
    {
        return false;
    }

    @Override
    public void login(final String username, final String password)
        throws ServletException
    {
    }

    @Override
    public void logout() throws ServletException {
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(final String name)
        throws IOException, ServletException
    {
        return null;
    }

    @Override
    public Object getAttribute(final String name)
    {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(final String env)
        throws UnsupportedEncodingException
    {
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(final String name)
    {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(final String name)
    {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return "HTTP/1.1";
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 80;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(final String name, final Object o)
    {
    }

    @Override
    public void removeAttribute(String name) {
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(final String path)
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
    public long getContentLengthLong()
    {
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation uses {@link ServletContext#getReadlPath}.</p>
     */
    @Override
    @Deprecated
    public String getRealPath(final String path)
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
     * <p>This implementation returns the local host name ({@value "localhost"}
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
     * <p>This implementation returns the local IP address ({@value
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
     * <p>This implementation always returns {@link DispathcerType#REQUEST}.
     * Subclasses may override this method to return another value.</p>
     *
     * @return {@link DispatcherType#REQUEST}
     */
    @Override
    public DispatcherType getDispatcherType()
    {
        return DispatcherType.REQUEST;
    }
}
