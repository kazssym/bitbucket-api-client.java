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
import java.net.URISyntaxException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.vx68k.bitbucket.api.client.Service;
import org.vx68k.bitbucket.api.client.BitbucketUser;
import org.vx68k.bitbucket.api.client.oauth.OAuthClient;
import org.vx68k.bitbucket.api.client.oauth.OAuthUser;

/**
 * User of the current session.
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
@Named("user")
public class SessionUser extends OAuthUser {

    private static final long serialVersionUID = 1L;

    private ApplicationConfig applicationConfig;

    /**
     * Constructs this instance.
     */
    public SessionUser() {
    }

    /**
     * Constructs this instance with an application configuration.
     * This constructor is equivalent to the default one followed by a call to
     * {@link #setApplicationConfig}.
     * @param applicationConfig application configuration
     * @since 4.0
     */
    public SessionUser(final ApplicationConfig applicationConfig) {
        setApplicationConfig(applicationConfig);
    }

    /**
     * Returns the application configuration.
     * @return application configuration
     * @since 4.0
     */
    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    /**
     * Sets the application configuration.
     * @param value application configuration to be set
     * @since 4.0
     */
    @Inject
    public void setApplicationConfig(final ApplicationConfig value) {
        applicationConfig = value;
    }

    /**
     * Indicates whether a user is authenticated or not.
     * @return <code>true</code> if a user is authenticated, or
     * <code>false</code> otherwise
     * @deprecated As of version 4.0, replaced by {@link #getBitbucketService}
     * and {@link Service#isAuthenticated} instead.
     */
    @Deprecated
    public boolean isAuthenticated() {
        return getBitbucketService().isAuthenticated();
    }

    /**
     * Returns the Bitbucket user of this instance.
     * @return Bitbucket user, or <code>null</code> if no user is authenticated
     * @throws IOException if an I/O error has occurred
     * @since 2.0
     * @deprecated As of version 4.0, replaced by {@link #getBitbucketService}
     * and {@link Service#getCurrentUser} instead.
     */
    @Deprecated
    public BitbucketUser getBitbucketUser() throws IOException {
        Service bitbucketService = getBitbucketService();
        return bitbucketService.getCurrentUser();
    }

    /**
     * Performs a login action by redirecting the user agent to the
     * authorization endpoint.
     * @return <code>null</code>
     * @throws URISyntaxException if a URI syntax error has been occurred
     * @throws IOException if an I/O error has occurred
     */
    public String login() throws URISyntaxException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request =
                (HttpServletRequest) externalContext.getRequest();

        // Redirects the user agent to the authorization endpoint.
        String uri = authorizationRequestUri(request);
        externalContext.redirect(uri);

        return null;
    }

    /**
     * Performs a logout action by clearing the current Bitbucket service.
     * @return <code>"home"</code>
     * @since 2.0
     */
    public String logout() {
        clearBitbucketService();

        return "home";
    }

    @Override
    protected OAuthClient getBitbucketClient() {
        return applicationConfig.getBitbucketClient();
    }
}
