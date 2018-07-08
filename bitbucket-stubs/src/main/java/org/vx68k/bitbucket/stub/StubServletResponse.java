/*
 * StubServletResponse.java - class StubServletResponse
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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * Stub implementation of {@link ServletResponse}.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class StubServletResponse implements ServletResponse
{
    /**
     * Output stream for the response body content.
     */
    private final ServletOutputStream outputStream;

    /**
     * Indicates if this response is committed.
     */
    private boolean committed = false;

    /**
     * Content type.
     */
    private String contentType = null;

    /**
     * Character encoding.
     */
    private String characterEncoding = "ISO-8859-1";

    /**
     * Locale.
     */
    private Locale locale = Locale.getDefault();

    /**
     * Constructs this object with a {@link ServletOutputStream} object.
     *
     * @param stream {@link ServletOutputStream} object
     */
    public StubServletResponse(final ServletOutputStream stream)
    {
        outputStream = stream;
    }

    /**
     * Marks this response is committed.
     */
    protected final void commit()
    {
        committed = true;
    }

    /**
     * Checks if this response is not committed.
     *
     * @exception IllegalStateException if this response is committed
     */
    protected final void checkNotCommitted()
    {
        if (isCommitted()) {
            throw new IllegalStateException("Already committed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ServletOutputStream getOutputStream()
    {
        return outputStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PrintWriter getWriter()
    {
        return new PrintWriter(new OutputStreamWriter(outputStream));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset()
    {
        checkNotCommitted();
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
        checkNotCommitted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isCommitted()
    {
        return committed;
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
    public void setBufferSize(final int size)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getContentType()
    {
        return contentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setContentType(final String value)
    {
        if (!isCommitted()) {
            contentType = value;
            // @todo Also set the character encoding.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getCharacterEncoding()
    {
        return characterEncoding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCharacterEncoding(final String value)
    {
        if (!isCommitted()) {
            characterEncoding = value;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Locale getLocale()
    {
        return locale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setLocale(final Locale value)
    {
        if (!isCommitted()) {
            locale = value;
            // @todo Also set the character encoding.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentLength(final int contentLength)
    {
        if (!isCommitted()) {
            // TODO: Set the Content-Length header.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentLengthLong(final long len)
    {
    }
}
