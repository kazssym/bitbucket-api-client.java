/*
 * StubHttpServletResponse.java - class StubHttpServletResponse
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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Stub implementation of {@link HttpServletResponse}.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class StubHttpServletResponse implements HttpServletResponse
{
    /**
     * Indicates if this response is committed.
     */
    private boolean committed = false;

    /**
     * HTTP status code.
     */
    private int status = SC_OK;

    /**
     * Content type.
     */
    private String contentType = null;

    /**
     * Content.
     */
    private byte[] content = new byte[0];

    /**
     * Sent HTTP error.
     */
    private Error sentError = null;

    /**
     * Returns the sent HTTP error.
     *
     * @return the sent errort
     */
    public Error getSentError()
    {
        return sentError;
    }

    /**
     * Marks this response is committed.
     */
    protected void commit()
    {
        committed = true;
    }

    /**
     * Checks if this response is not committed.
     *
     * @exception IllegalStateException if this response had been committed
     */
    protected void checkIfNotCommitted()
    {
        if (isCommitted()) {
            throw new IllegalStateException("Already committed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCookie(final Cookie cookie)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsHeader(final String name)
    {
        // TODO: Check the header name.
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns the given URL unchanged.
     * Subclasses may override this method to return other values.</p>
     *
     * @param url URL to be encoded
     * @return the given URL unchanged
     */
    @Override
    public String encodeURL(final String url)
    {
        return url;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always returns the given URL unchanged.
     * Subclasses may override this method to return other values.</p>
     *
     * @param url URL to be encoded
     * @return the given URL unchanged
     */
    @Override
    public String encodeRedirectURL(final String url)
    {
        return url;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation uses {@link #encodeURL encodeURL} internally.</p>
     */
    @Override
    @Deprecated
    public final String encodeUrl(final String url)
    {
        return encodeURL(url);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation uses {@link #encodeRedirectURL encodeRedirectURL}
     * internally.</p>
     */
    @Override
    @Deprecated
    public final String encodeRedirectUrl(final String url)
    {
        return encodeRedirectURL(url);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendError(final int status, final String message)
        throws IOException
    {
        checkIfNotCommitted();
        setStatus(status);
        sentError = new Error(status, message);

        setContentType("text/html");
        commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void sendError(final int status) throws IOException
    {
        sendError(status, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendRedirect(final String location) throws IOException
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDateHeader(final String name, final long date)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDateHeader(final String name, final long date)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeader(final String name, final String value)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addHeader(final String name, final String value)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIntHeader(final String name, final int value)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIntHeader(final String name, final int value)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(final int value)
    {
        if (!isCommitted()) {
            status = value;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public void setStatus(final int status, final String message)
    {
        setStatus(status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatus()
    {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(final String name)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaders(final String name)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaderNames()
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
    public String getContentType()
    {
        return contentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintWriter getWriter() throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacterEncoding(final String charset)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentLength(final int contentLength)
    {
        if (!isCommitted()) {
            if (contentLength != content.length) {
                content = Arrays.copyOf(content, contentLength);
            }
            // TODO: Set the Content-Length header.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentType(final String value)
    {
        if (!isCommitted()) {
            contentType = value;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBufferSize(final int size)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBufferSize()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flushBuffer() throws IOException
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetBuffer()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCommitted()
    {
        return committed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocale(final Locale loc)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getLocale()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentLengthLong(final long len)
    {
    }

    /**
     * Error information.
     */
    public static class Error
    {
        /**
         * HTTP status code.
         */
        private final int status;

        /**
         * HTTP error message.
         */
        private final String message;

        /**
         * Constructs this error with a status and a message.
         *
         * @param initStatus HTTP error status
         * @param initMessage HTTP error message
         */
        public Error(final int initStatus, final String initMessage)
        {
            status = initStatus;
            message = initMessage;
        }

        /**
         * Returns the HTTP status code.
         *
         * @return the HTTP status
         */
        public final int getStatus()
        {
            return status;
        }

        /**
         * Returns the HTTP error message.
         *
         * @return the HTTP error message.
         */
        public final String getMessage()
        {
            return message;
        }
    }
}