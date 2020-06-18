/*
 * UserContext.java - class UserContext
 * Copyright (C) 2015-2018 Kaz Nishimura
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

package org.vx68k.bitbucket.webapp;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.vx68k.bitbucket.BitbucketUserAccount;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * Session-scoped bean for the current user of the session.
 * <p>To enable authentication by OAuth, the deployment descriptor must be
 * configured as follows:</p>
 * <pre>
 * &lt;env-entry&gt;
 * &lt;env-entry-name&gt;bitbucketClientId&lt;/env-entry-name&gt;
 * &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 * &lt;lookup-name&gt;java:global/env/bitbucketClientId&lt;/lookup-name&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 * &lt;env-entry-name&gt;bitbucketClientSecret&lt;/env-entry-name&gt;
 * &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 * &lt;lookup-name&gt;java:global/env/bitbucketClientSecret&lt;/lookup-name&gt;
 * &lt;/env-entry&gt;
 * </pre>
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@SuppressWarnings({"designForExtension"})
@Named
@SessionScoped
public class UserContext implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Default HTTP port.
     */
    private static final int HTTP_PORT = 80;

    /**
     * Default HTTPS port.
     */
    private static final int HTTPS_PORT = 443;

    /**
     * Prefix for property keys.
     */
    private static final String PROPERTY_PREFIX = "org.vx68k.bitbucket.";

    /**
     * Property key for the OAuth client identifier.
     */
    public static final String BITBUCKET_CLIENT_ID =
        PROPERTY_PREFIX + "clientId";

    /**
     * Property key for the OAuth client secret.
     */
    public static final String BITBUCKET_CLIENT_SECRET =
        PROPERTY_PREFIX + "clientSecret";

    /**
     * {@link BitbucketClient} object.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * {@link BitbucketUserAccount} object for the current user, or {@code null} if
     * not logged in.
     */
    private BitbucketUserAccount loggedInUser = null;

    /**
     * Redirection endpoint URI.
     */
    private transient String redirectionURI = null;

    /**
     * Constructs this object with no parameters.
     */
    public UserContext()
    {
        this.bitbucketClient = new BitbucketClient();

        bitbucketClient.setClientId(
            System.getProperty(BITBUCKET_CLIENT_ID));
        bitbucketClient.setClientSecret(
            System.getProperty(BITBUCKET_CLIENT_SECRET));
    }

    /**
     * Returns the {@link BitbucketClient} object.
     *
     * @return the {@link BitbucketClient} object
     */
    public BitbucketClient getBitbucketClient()
    {
        return bitbucketClient;
    }

    /**
     * Returns the OAuth client identifier for the Bitbucket API.
     * The return value may be {@code null} if not configured.
     *
     * @return the OAuth client identifier for the Bitbucket API, or {@code
     * null}
     */
    public String getBitbucketClientId()
    {
        return bitbucketClient.getClientId();
    }

    /**
     * Sets the OAuth client identifier for the Bitbucket API.
     *
     * @param value {@code String} value to which the OAuth client identifier
     * shall be set
     */
    @Resource(name = "bitbucketClientId", type = String.class,
        description = "OAuth client identifier for the Bitbucket API.")
    public void setBitbucketClientId(final String value)
    {
        bitbucketClient.setClientId(value);
    }

    /**
     * Sets the OAuth client secret for the Bitbucket API.
     *
     * @param value {@code String} value to which the OAuth client secret shall
     * be set
     */
    @Resource(name = "bitbucketClientSecret", type = String.class,
        description = "OAuth client secret for the Bitbucket API.")
    public void setBitbucketClientSecret(final String value)
    {
        bitbucketClient.setClientSecret(value);
    }

    /**
     * Returns the logged-in user.
     *
     * @return the logged-in user
     */
    public BitbucketUserAccount getLoggedInUser()
    {
        return loggedInUser;
    }

    /**
     * Returns {@code true} if the current user is logged in.
     *
     * @return {@code true} if logged in, or {@code false} otherwise
     */
    public boolean isLoggedIn()
    {
        return bitbucketClient.getAccessToken() != null;
    }

    /**
     * Constructs the origin of the server.
     *
     * @param externalContext {@link ExternalContext} object
     * @return {@link URI} object of the origin
     */
    protected static URI getOrigin(final ExternalContext externalContext)
    {
        String scheme = externalContext.getRequestScheme();
        String serverName = externalContext.getRequestServerName();
        int serverPort = externalContext.getRequestServerPort();
        if (scheme.equals("http") && serverPort == HTTP_PORT) {
            serverPort = -1;
        }
        else if (scheme.equals("https") && serverPort == HTTPS_PORT) {
            serverPort = -1;
        }
        try {
            return new URI(
                scheme, null, serverName, serverPort, null, null, null);
        }
        catch (final URISyntaxException exception) {
            throw new FacesException("Unexpected exception", exception);
        }
    }

    /**
     * Performs a login action by redirecting the user agent to the
     * authorization endpoint.
     *
     * @return {@code null}
     */
    public String login()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        String clientId = getBitbucketClientId();
        if (clientId == null) {
            // @todo Fail gracefully?
            throw new FacesException();
        }

        ExternalContext externalContext = facesContext.getExternalContext();
        UIViewRoot view = facesContext.getViewRoot();
        ViewHandler viewHandler =
            facesContext.getApplication().getViewHandler();
        redirectionURI = viewHandler.getRedirectURL(
            facesContext, view.getViewId(), emptyMap(), true);

        // The redirection endpoint URI must be absolute.
        URI origin = getOrigin(externalContext);
        redirectionURI = origin.resolve(redirectionURI).toString();

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("response_type", singletonList("code"));
        parameters.put("client_id", singletonList(clientId));
        parameters.put("redirect_uri", singletonList(redirectionURI));

        String authorizationURI = externalContext.encodeRedirectURL(
            BitbucketClient.AUTHORIZATION_ENDPOINT_URI.toString(), parameters);
        externalContext.log("Redirecting to " + authorizationURI);
        try {
            externalContext.redirect(authorizationURI);
        }
        catch (final IOException exception) {
            throw new FacesException("Redirection failure", exception);
        }

        return null;
    }

    /**
     * Performs a logout action by clearing the current Bitbucket service.
     * @return <code>"home"</code>
     * @since 2.0
     */
    public String logout()
    {
//        clearBitbucketService();

        return "home";
    }

    /**
     * Continues the current authorization flow to login.
     *
     * @param code an authorization code
     * @param state an opaque state string
     */
    public void login(final String code, final String state)
    {
        if (redirectionURI != null) {
            // @todo Check {@code state}.
            bitbucketClient.loginWithAuthorizationCode(
                code, URI.create(redirectionURI));
            redirectionURI = null;
        }
    }

    /**
     * Aborts the current authorization flow.
     *
     * @param errorDescription an error description
     * @param state an opaque state string
     */
    public void abort(final String errorDescription, final String state)
    {
        if (redirectionURI != null) {
            // @todo Check {@code state}.
            redirectionURI = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int k = 257;
        int value = getClass().hashCode();
        value = k * value + Objects.hashCode(bitbucketClient);
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object)
    {
        if (this != object) {
            if (object == null || object.getClass() != getClass()) {
                return false;
            }

            UserContext other = (UserContext) object;
            if (!Objects.equals(bitbucketClient, other.bitbucketClient)) {
                return false;
            }
            if (!Objects.equals(loggedInUser, other.loggedInUser)) {
                return false;
            }
        }
        return true;
    }
}
