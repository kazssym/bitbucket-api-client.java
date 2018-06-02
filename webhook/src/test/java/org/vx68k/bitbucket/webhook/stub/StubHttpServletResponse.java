/*
 * StubHttpServletResponse
 * Copyright (C) 2015 Nishimura Software Studio
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
 * Stub for {@link HttpServletResponse}
 *
 * @author Kaz Nishimura
 * @since 1.0
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
    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsHeader(String name) {
        // TODO: Check the header name.
        return false;
    }

    @Override
    public String encodeURL(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String encodeRedirectURL(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String encodeUrl(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String encodeRedirectUrl(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendError(int status, String message) throws IOException {
        checkIfNotCommitted();
        setStatus(status);
        sentError = new Error(status, message);

        setContentType("text/html");
        commit();
    }

    @Override
    public void sendError(int status) throws IOException {
        sendError(status, null);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDateHeader(String name, long date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addDateHeader(String name, long date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addHeader(String name, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIntHeader(String name, int value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addIntHeader(String name, int value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setStatus(int status) {
        if (!isCommitted()) {
            this.status = status;
        }
    }

    @Deprecated
    @Override
    public void setStatus(int status, String message) {
        setStatus(status);
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getHeader(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<String> getHeaders(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<String> getHeaderNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCharacterEncoding(String charset) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setContentLength(int contentLength) {
        if (!isCommitted()) {
            if (contentLength != content.length) {
                content = Arrays.copyOf(content, contentLength);
            }
            // TODO: Set the Content-Length header.
        }
    }

    @Override
    public void setContentType(String contentType) {
        if (!isCommitted()) {
            this.contentType = contentType;
        }
    }

    @Override
    public void setBufferSize(int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getBufferSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetBuffer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLocale(Locale loc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setContentLengthLong(long len)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static class Error {

        private final int status;
        private final String message;

        public Error(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
