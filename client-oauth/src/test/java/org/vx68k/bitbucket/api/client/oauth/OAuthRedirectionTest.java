/*
 * OAuthRedirectionTest
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

package org.vx68k.bitbucket.api.client.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collection of unit tests for {@link OAuthRedirection}.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class OAuthRedirectionTest {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() {
        request = new StubRequest();
        response = new StubResponse();
    }

    @After
    public void tearDown() {
        response = null;
        request = null;
    }

    @Test
    public void testInitialize() {
        OAuthRedirection redirection =
                new OAuthRedirection(request, response);
        assertEquals(request, redirection.getRequest());
        assertEquals(response, redirection.getResponse());
    }

    static class StubRequest implements HttpServletRequest {

        @Override
        public String getAuthType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Cookie[] getCookies() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long getDateHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getIntHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getMethod() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getPathInfo() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getPathTranslated() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getContextPath() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getQueryString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRemoteUser() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isUserInRole(String role) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Principal getUserPrincipal() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRequestedSessionId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRequestURI() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public StringBuffer getRequestURL() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getServletPath() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public HttpSession getSession(boolean create) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public HttpSession getSession() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean authenticate(HttpServletResponse response)
                throws ServletException, IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void login(String user, String password)
                throws ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void logout() throws ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Collection<Part> getParts()
                throws ServletException, IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Part getPart(String name)
                throws ServletException, IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object getAttribute(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getCharacterEncoding() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setCharacterEncoding(String env)
                throws UnsupportedEncodingException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getContentLength() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getContentType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getParameter(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<String> getParameterNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String[] getParameterValues(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getProtocol() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getScheme() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getServerName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getServerPort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BufferedReader getReader() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRemoteAddr() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRemoteHost() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setAttribute(String name, Object o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void removeAttribute(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Locale getLocale() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Enumeration<Locale> getLocales() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isSecure() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getRealPath(String path) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getRemotePort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getLocalName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getLocalAddr() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getLocalPort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ServletContext getServletContext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AsyncContext startAsync(
                ServletRequest servletRequest,
                ServletResponse servletResponse)
                throws IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isAsyncStarted() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isAsyncSupported() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public AsyncContext getAsyncContext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DispatcherType getDispatcherType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    protected static class StubResponse implements HttpServletResponse {

        @Override
        public void addCookie(Cookie cookie) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean containsHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
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
        public void sendError(int sc, String msg) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void sendError(int sc) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
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
        public void setStatus(int sc) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setStatus(int sc, String sm) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getStatus() {
            throw new UnsupportedOperationException("Not supported yet.");
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
            throw new UnsupportedOperationException("Not supported yet.");
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
        public void setContentLength(int len) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setContentType(String type) {
            throw new UnsupportedOperationException("Not supported yet.");
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
            throw new UnsupportedOperationException("Not supported yet.");
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
    }
}
