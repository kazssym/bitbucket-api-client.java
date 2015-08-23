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
import java.net.UnknownServiceException;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Service;

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

    private transient Service bitbucketService;

    public SessionUser() {
    }

    public SessionUser(ApplicationConfig config) throws IOException {
        setConfig(config);
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
     * Indicates whether a user is authenticated or not.
     *
     * @return <code>true</code> if a user is authenticated, or
     * <code>false</code> otherwise
     */
    public boolean isAuthenticated() {
        if (bitbucketService == null) {
            return false;
        }
        return bitbucketService.isAuthenticated();
    }

    @Inject
    public void setConfig(ApplicationConfig applicationConfig)
            throws IOException {
        this.applicationConfig = applicationConfig;
    }

    public String login() throws IOException {
        Client bitbucketClient = applicationConfig.getBitbucketClient();
        AuthorizationCodeFlow flow
                = bitbucketClient.getAuthorizationCodeFlow(false);
        if (flow == null) {
            throw new IllegalStateException("No client credentials");
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);

        AuthorizationRequestUrl requestUrl = flow.newAuthorizationUrl();
        requestUrl.setState(session.getId());
        externalContext.redirect(requestUrl.build());

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
                    bitbucketService = bitbucketClient.getService(code);

                    HttpServletResponse response = redirection.getResponse();
                    response.sendRedirect(request.getContextPath() + "/");
                }
            }
        }
    }
}
