/*
 * StubServletRequest.java - class StubServletRequest
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
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Stub implementation of {@link ServletRequest}.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class StubServletRequest implements ServletRequest
{
    /**
     * Default HTTP server port.
     */
    public static final int HTTP_PORT = 80;

    /**
     * Default HTTPS server port.
     */
    public static final int HTTPS_PORT = 443;

    /**
     * Default local host address.
     */
    private static final InetAddress LOCAL = InetAddress.getLoopbackAddress();

    /**
     * {@link ServletContext} object given to the constructor.
     */
    private final ServletContext servletContext;

    /**
     * Input stream.
     */
    private final ServletInputStream inputStream;

    /**
     * Attributes.
     */
    private final Map<String, Object> attributes;

    /**
     * Server name.
     */
    private String serverName = LOCAL.getHostName();

    /**
     * Server port.
     */
    private int serverPort = HTTP_PORT;

    /**
     * Remote host address.
     */
    private String remoteAddr = LOCAL.getHostAddress();

    /**
     * Remote host name.
     */
    private String remoteHost = LOCAL.getHostName();

    /**
     * Remote TCP port.
     */
    private int remotePort = 0;

    /**
     * Local host address.
     */
    private String localAddr = LOCAL.getHostAddress();

    /**
     * Local host name.
     */
    private String localName = LOCAL.getHostName();

    /**
     * Local TCP port.
     */
    private int localPort = HTTP_PORT;

    /**
     * Constructs this object with a {@link ServletContext} object.
     * The input stream shall be set to empty.
     *
     * @param context {@link ServletContext} object
     */
    public StubServletRequest(final ServletContext context)
    {
        this(context, new StubServletInputStream());
    }

    /**
     * Constructs this object with a {@link ServletContext} object and a {@link
     * ServletInputStream} object.
     *
     * @param context {@link ServletContext} object
     * @param stream {@link ServletInputStream} object
     */
    public StubServletRequest(final ServletContext context,
        final ServletInputStream stream)
    {
        servletContext = context;
        inputStream = stream;
        attributes = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the {@link ServletContext} object given
     * to the constructor.</p>
     */
    @Override
    public final ServletContext getServletContext()
    {
        return servletContext;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the {@link ServletInputStream} object
     * given to the constructor.</p>
     */
    @Override
    public final ServletInputStream getInputStream()
    {
        return inputStream;
    }

    /**
     * Sets the server name to a {@link String} value.
     *
     * @param value {@link String} value to which the server name shall be set
     */
    public final void setServerName(final String value)
    {
        serverName = value;
    }

    /**
     * Sets the server port to an {@code int} value.
     *
     * @param value {@code int} value to which the server port shall be set
     */
    public final void setServerPort(final int value)
    {
        serverPort = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object getAttribute(final String name)
    {
        return attributes.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Enumeration<String> getAttributeNames()
    {
        return Collections.enumeration(attributes.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setAttribute(final String name, final Object object)
    {
        attributes.put(name, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removeAttribute(final String name)
    {
        attributes.remove(name);
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
        return "http";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getServerName()
    {
        return serverName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getServerPort()
    {
        return serverPort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BufferedReader getReader() throws IOException
    {
        String charset = getCharacterEncoding();
        if (charset == null) {
            charset = "ISO-8859-1";
        }
        return new BufferedReader(
            new InputStreamReader(getInputStream(), charset));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRemoteAddr()
    {
        return remoteAddr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRemoteHost()
    {
        String value = remoteHost;
        if (value == null) {
            value = getRemoteAddr();
        }
        return value;
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
