/*
 * OAuthUser
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

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.event.Observes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Service;

/**
 * User with OAuth authorization by Bitbucket.
 * <em>As of version 4.0, this class was changed to abstract.</em>
 * @author Kaz Nishimura
 * @since 3.0
 */
public abstract class OAuthUser implements Serializable {

    private static final long serialVersionUID = 3L;

    /**
     * Scheme of HTTP.
     */
    private static final String HTTP_SCHEME = "http";

    /**
     * Scheme of HTTPS.
     */
    private static final String HTTPS_SCHEME = "https";

    /**
     * Default port for the HTTP scheme.
     */
    private static final int DEFAULT_HTTP_PORT = 80;

    /**
     * Default port for the HTTPS scheme.
     */
    private static final int DEFAULT_HTTPS_PORT = 443;

    private transient Service bitbucketService;

    /**
     * Constructs this object.
     */
    protected OAuthUser() {
    }

    /**
     * Returns a Bitbucket client for getting a Bitbucket service.
     * @return Bitbucket client that supports OAuth authorization
     * @since 4.0
     */
    protected abstract OAuthClient getBitbucketClient();

    /**
     * Returns the current Bitbucket service.
     * If there is no current Bitbucket service, this method shall create an
     * anonymous one.
     * @return current Bitbucket service
     */
    public Service getBitbucketService() {
        synchronized (this) {
            if (bitbucketService == null) {
                Client bitbucketClient = getBitbucketClient();
                bitbucketService = bitbucketClient.getService();
            }
        }
        return bitbucketService;
    }

    /**
     * Returns the authorization endpoint URI.
     * @param request HTTP request
     * @return authorization endpoint URI
     * @throws URISyntaxException if a URI syntax error has been occurred
     * @throws IOException if an I/O error has occurred
     * @since 5.0
     */
    protected String authorizationRequestUri(HttpServletRequest request)
            throws URISyntaxException, IOException {
        URI redirectionEndpoint = new URI(
                request.getScheme(), null, request.getServerName(),
                getExplicitServerPort(request), getRedirectionPath(request),
                null, null);

        HttpSession session = request.getSession();
        OAuthClient bitbucketClient = getBitbucketClient();
        return bitbucketClient.authorizationRequestUri(
                redirectionEndpoint, session.getId());
    }

    protected void requestToken(@Observes OAuthRedirection redirection)
            throws IOException {
        HttpServletRequest request = redirection.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String state = request.getParameter("state");
            if (state != null && state.equals(session.getId())) {
                // The redirection is for this session.
                HttpServletResponse response = redirection.getResponse();

                String authorizationCode = request.getParameter("code");
                if (authorizationCode != null) {
                    // The resource access was authorized.
                    OAuthClient bitbucketClient = getBitbucketClient();
                    synchronized (this) {
                        bitbucketService = bitbucketClient.getService(
                                authorizationCode);
                    }

                    StringBuilder path = new StringBuilder(
                            request.getContextPath());
                    if (request.getPathInfo() != null) {
                        path.append(request.getPathInfo());
                    }
                    response.sendRedirect(path.toString());
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    /**
     * Clears the current Bitbucket service.
     */
    protected void clearBitbucketService() {
        synchronized (this) {
            bitbucketService = null;
        }
    }

    /**
     * Returns the server port of a HTTP request.
     * @param request HTTP request
     * @return server port, or -1 if it is the default of the scheme
     */
    protected static int getExplicitServerPort(HttpServletRequest request) {
        int port = request.getServerPort();
        if (port == DEFAULT_HTTP_PORT
                && request.getScheme().equals(HTTP_SCHEME)) {
            return -1;
        }
        if (port == DEFAULT_HTTPS_PORT
                && request.getScheme().equals(HTTPS_SCHEME)) {
            port = -1;
        }
        return port;
    }

    /**
     * Returns the redirection endpoint path.
     * @param request HTTP request
     * @return redirection endpoint path
     */
    protected static String getRedirectionPath(HttpServletRequest request) {
        StringBuilder path = new StringBuilder(request.getContextPath());
        path.append("/authorized");
        if (request.getServletPath() != null) {
            path.append(request.getServletPath());
        }
        if (request.getPathInfo() != null) {
            path.append(request.getPathInfo());
        }
        return path.toString();
    }
}
