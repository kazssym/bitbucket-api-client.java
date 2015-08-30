/*
 * SessionUser
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

package org.vx68k.bitbucket.api.client.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Service;
import org.vx68k.bitbucket.api.client.User;
import org.vx68k.bitbucket.api.client.oauth.OAuthRedirection;

/**
 * User of the current session.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
@Named("user")
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private ApplicationConfig applicationConfig;

    private transient Service service;

    public SessionUser() {
    }

    public SessionUser(ApplicationConfig config) throws IOException {
        setApplicationConfig(config);
    }

    /**
     * Returns the application configuration.
     *
     * @return application configuration
     */
    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    /**
     * Returns the current Bitbucket service.
     * If there is no current service, an anonymous service shall be created.
     * @return current service
     */
    protected Service getService() {
        synchronized (this) {
            if (service == null) {
                Client client = applicationConfig.getBitbucketClient();
                service = client.getService();
            }
        }
        return service;
    }

    /**
     * Indicates whether a user is authenticated or not.
     * @return <code>true</code> if a user is authenticated, or
     * <code>false</code> otherwise
     * @deprecated As of version 2.0, use {#getBitbucketUser} instead.
     */
    @Deprecated
    public boolean isAuthenticated() {
        return getService().isAuthenticated();
    }

    /**
     * Returns the Bitbucket user of this object.
     * @return Bitbucket user, or <code>null</code> if no user is authenticated
     * @throws IOException if an I/O error has occurred
     * @since 2.0
     */
    public User getBitbucketUser() throws IOException {
        return getService().getCurrentUser();
    }

    @Inject
    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    /**
     * Handles a login action by redirecting the user agent to the
     * authorization endpoint.
     * @return <code>null</code>
     * @throws URISyntaxException if the authorization endpoint could not be
     * parsed as a URI
     * @throws IOException if an I/O error occurred
     * @since 1.0
     */
    public String login() throws URISyntaxException, IOException {
        Client bitbucketClient = applicationConfig.getBitbucketClient();

        // Redirects the user agent to the authorization endpoint.
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        URI authorizationEndpoint
                = bitbucketClient.getAuthorizationEndpoint(session.getId());
        externalContext.redirect(authorizationEndpoint.toString());

        return null;
    }

    public void requestToken(@Observes OAuthRedirection redirection)
            throws IOException {
        HttpServletRequest request = redirection.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String state = request.getParameter("state");
            if (state != null && state.equals(session.getId())) {
                // The redirection is for this session.

                String code = request.getParameter("code");
                if (code != null) {
                    // The resource access was authorized.
                    Client bitbucketClient
                            = applicationConfig.getBitbucketClient();
                    synchronized (this) {
                        service = bitbucketClient.getService(code);
                    }

                    HttpServletResponse response = redirection.getResponse();
                    response.sendRedirect(request.getContextPath() + "/");
                }
            }
        }
    }

    /**
     * Handles a logout action by disassociating the Bitbucket service.
     * @return <code>null</code>
     * @since 2.0
     */
    public String logout() {
        synchronized (this) {
            service = null;
        }

        return null;
    }
}
