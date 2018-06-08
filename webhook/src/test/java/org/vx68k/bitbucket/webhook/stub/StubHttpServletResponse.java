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

package org.vx68k.bitbucket.webhook.stub;

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
public class StubHttpServletResponse implements HttpServletResponse {

    private boolean committed = false;

    private int status = SC_OK;

    private String contentType = null;

    private byte[] content = new byte[0];

    private Error sentError = null;

    public Error getSentError() {
        return sentError;
    }

    protected void commit() {
        committed = true;
    }

    protected void checkIfNotCommitted() {
        if (isCommitted()) {
            throw new IllegalStateException("Already committed");
        }
    }

    @Override
    public void addCookie(final Cookie cookie) {
    }

    @Override
    public boolean containsHeader(final String name) {
        // TODO: Check the header name.
        return false;
    }

    @Override
    public String encodeURL(final String url) {
        return null;
    }

    @Override
    public String encodeRedirectURL(final String url) {
        return null;
    }

    @Override
    public String encodeUrl(final String url) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(final String url) {
        return null;
    }

    @Override
    public void sendError(final int status, final String message) throws IOException {
        checkIfNotCommitted();
        setStatus(status);
        sentError = new Error(status, message);

        setContentType("text/html");
        commit();
    }

    @Override
    public void sendError(final int status) throws IOException {
        sendError(status, null);
    }

    @Override
    public void sendRedirect(final String location) throws IOException {
    }

    @Override
    public void setDateHeader(final String name, final long date) {
    }

    @Override
    public void addDateHeader(final String name, final long date) {
    }

    @Override
    public void setHeader(final String name, final String value) {
    }

    @Override
    public void addHeader(final String name, final String value) {
    }

    @Override
    public void setIntHeader(final String name, final int value) {
    }

    @Override
    public void addIntHeader(final String name, final int value) {
    }

    @Override
    public void setStatus(final int value) {
        if (!isCommitted()) {
            status = value;
        }
    }

    @Deprecated
    @Override
    public void setStatus(final int status, final String message) {
        setStatus(status);
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getHeader(final String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(final String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(final String charset) {
    }

    @Override
    public void setContentLength(final int contentLength) {
        if (!isCommitted()) {
            if (contentLength != content.length) {
                content = Arrays.copyOf(content, contentLength);
            }
            // TODO: Set the Content-Length header.
        }
    }

    @Override
    public void setContentType(final String value) {
        if (!isCommitted()) {
            contentType = value;
        }
    }

    @Override
    public void setBufferSize(final int size) {
    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
    }

    @Override
    public void resetBuffer() {
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    @Override
    public void reset() {
    }

    @Override
    public void setLocale(final Locale loc) {
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public void setContentLengthLong(final long len)
    {
    }

    public static class Error {

        private final int status;
        private final String message;

        public Error(final int initStatus, final String initMessage) {
            status = initStatus;
            message = initMessage;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
