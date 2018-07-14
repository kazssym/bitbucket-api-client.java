/*
 * CurrentUser.java - class CurrentUser
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

package org.vx68k.bitbucket.api.client.example;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * Session-scoped bean for the current user of the session.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@Named
@SessionScoped
public class CurrentUser implements BitbucketUser, Serializable
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
     * OAuth authorization endpoint URI for the Bitbucket API.
     */
    private static final String BITBUCKET_AUTHORIZATION_ENDPOINT =
        "https://bitbucket.org/site/oauth2/authorize";

    /**
     * OAuth token endpoint URI for the Bitbucket API.
     */
    private static final String BITBUCKET_TOKEN_ENDPOINT =
        "https://bitbucket.org/site/oauth2/access_token";

    /**
     * {@link BitbucketClient} object.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * OAuth client identifier for the Bitbucket API.
     */
    private String bitbucketClientId = null;

    /**
     * OAuth client secret for the Bitbucket API.
     */
    private String bitbucketClientSecret = null;

    /**
     * {@link BitbucketUser} object for the current user, or {@code null} if
     * not logged in.
     */
    private BitbucketUser loggedInUser = null;

    /**
     * Constructs this object with no parameters.
     */
    public CurrentUser()
    {
        bitbucketClient = new BitbucketClient();
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
        return bitbucketClientId;
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
        bitbucketClientId = value;
    }

    /**
     * Returns the OAuth client secret for the Bitbucket API.
     * The return value may be {@code null} if not configured.
     *
     * @return the OAuth client secret for the Bitbucket API, or {@code null}
     */
    public String getBitbucketClientSecret()
    {
        return bitbucketClientSecret;
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
        bitbucketClientSecret = value;
    }

    /**
     * Returns {@code true} if the current user is logged in.
     *
     * @return {@code true} if logged in, or {@code false} otherwise
     */
    public boolean isLoggedIn()
    {
        return loggedInUser != null;
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
        ExternalContext externalContext = facesContext.getExternalContext();
        UIViewRoot view = facesContext.getViewRoot();
        ViewHandler viewHandler =
            facesContext.getApplication().getViewHandler();

        String clientId = getBitbucketClientId();
        String redirectionURI = viewHandler.getRedirectURL(
            facesContext, view.getViewId(), emptyMap(), true);

        // The redirection URI must be absolute.
        URI origin = getOrigin(externalContext);
        redirectionURI = origin.resolve(redirectionURI).toString();

        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("response_type", singletonList("code"));
        parameters.put("client_id", singletonList(clientId));
        parameters.put("redirect_uri", singletonList(redirectionURI));

        String authorizationURI = externalContext.encodeRedirectURL(
            BITBUCKET_AUTHORIZATION_ENDPOINT, parameters);
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
     * {@inheritDoc}
     */
    @Override
    public String getType()
    {
        String value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getType();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        String value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getName();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUUID()
    {
        UUID value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getUUID();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName()
    {
        String value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getDisplayName();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWebsite()
    {
        String value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getWebsite();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocation()
    {
        String value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getLocation();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPrivate()
    {
        boolean value = false;
        if (loggedInUser != null) {
            value = loggedInUser.isPrivate();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant getCreated()
    {
        Instant value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getCreated();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getLinks()
    {
        Map<String, String> value = null;
        if (loggedInUser != null) {
            value = loggedInUser.getLinks();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BitbucketRepository> repositorySet()
    {
        Set<BitbucketRepository> value = null;
        if (loggedInUser != null) {
            value = loggedInUser.repositorySet();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int value = getClass().hashCode();
        assert bitbucketClient != null;
        value ^= bitbucketClient.hashCode();
        if (bitbucketClientId != null) {
            value ^= bitbucketClientId.hashCode();
        }
        if (bitbucketClientSecret != null) {
            value ^= bitbucketClientSecret.hashCode();
        }
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

            CurrentUser other = (CurrentUser) object;
            assert bitbucketClient != null;
            if (!bitbucketClient.equals(other.bitbucketClient)) {
                return false;
            }
            if (bitbucketClientId != null
                && !bitbucketClientId.equals(other.bitbucketClientId)) {
                return false;
            }
            else if (other.bitbucketClientId != null) {
                return false;
            }
            if (bitbucketClientSecret != null
                && !bitbucketClientSecret.equals(other.bitbucketClientSecret)) {
                return false;
            }
            else if (other.bitbucketClientSecret != null) {
                return false;
            }
        }
        return true;
    }
}
