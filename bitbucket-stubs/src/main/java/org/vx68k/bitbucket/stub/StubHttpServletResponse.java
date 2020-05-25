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
import java.util.Collection;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Stub implementation of {@link HttpServletResponse}.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class StubHttpServletResponse extends StubServletResponse
    implements HttpServletResponse
{
    /**
     * Content type for a HTML document.
     */
    private static final String TEXT_HTML = "text/html";

    /**
     * HTTP status code.
     */
    private int status = SC_OK;

    /**
     * Constructs this object with a {@link ServletOutputStream} object.
     *
     * @param stream {@link ServletOutputStream} object
     */
    public StubHttpServletResponse(final ServletOutputStream stream)
    {
        super(stream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getStatus()
    {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setStatus(final int value)
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
    public final void setStatus(final int statusCode, final String message)
    {
        setStatus(statusCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void sendError(final int statusCode, final String message)
        throws IOException
    {
        checkNotCommitted();
        setStatus(statusCode);
        setContentType(TEXT_HTML);
        // @todo Use {@code message} to make an error document.
        commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void sendError(final int statusCode) throws IOException
    {
        sendError(statusCode, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void sendRedirect(final String location) throws IOException
    {
        commit();
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
}
