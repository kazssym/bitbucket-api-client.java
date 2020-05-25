/*
 * StubServletInputStream.java - class StubServletInputStream
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

package org.vx68k.bitbucket.stub;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * Stub implementation of {@link ServletInputStream} which wraps {@link
 * InputStream}.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class StubServletInputStream extends ServletInputStream
{
    /**
     * {@link InputStream} object given to the constructor.
     */
    private final InputStream wrappedInputStream;

    /**
     * Number of remaining bytes.
     */
    private long remaining;

    /**
     * Constructs this object wrapping no {@link InputStream} object.
     */
    public StubServletInputStream()
    {
        this(null, 0);
    }

    /**
     * Constructs this object wrapping an {@link InputStream} object.
     *
     * @param inputStream {@link InputStream} object to wrap, or {@code null}
     * @param length length of the input stream
     */
    public StubServletInputStream(final InputStream inputStream,
        final long length)
    {
        wrappedInputStream = inputStream;
        remaining = length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isFinished()
    {
        return remaining > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isReady()
    {
        boolean value = true;
        if (wrappedInputStream != null && remaining > 0) {
            try {
                value = wrappedInputStream.available() > 0;
            }
            catch (IOException exception) {
                // Nothing to do.
            }
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReadListener(final ReadListener readListener)
    {
        throw new IllegalStateException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int read() throws IOException
    {
        int value = -1;
        if (wrappedInputStream != null && remaining > 0) {
            value = wrappedInputStream.read();
            if (value != -1) {
                remaining -= 1;
            }
        }
        return value;
    }
}
