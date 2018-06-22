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

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Current user of the session.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@SessionScoped
@Named("currentUser")
public class CurrentUser implements BitbucketUser, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * {@link BitbucketAPI} object given to the constructor.
     */
    private final BitbucketAPI bitbucketAPI;

    /**
     * Constructs this object with a {@link BitbucketAPI} object.
     *
     * @param bitbucket {@link BitbucketAPI object}
     */
    @Inject
    public CurrentUser(final BitbucketAPI bitbucket)
    {
        bitbucketAPI = bitbucket;
    }

    /**
     * Returns the {@link BitbucketAPI} object given to the constructor.
     *
     * @return the {@link BitbucketAPI} object
     */
    public final BitbucketAPI getBitbucketAPI()
    {
        return bitbucketAPI;
    }

    /**
     * Returns {@code true} if the current user is logged in.
     *
     * @return {@code true} if logged in, or {@code false} otherwise
     */
    public final boolean isLoggedIn()
    {
        // @todo Implement this method.
        return false;
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
    public BitbucketUser getBitbucketUser() throws IOException
    {
//        Service bitbucketService = getBitbucketService();
//        return bitbucketService.getCurrentUser();
        return null;
    }

    /**
     * Performs a login action by redirecting the user agent to the
     * authorization endpoint.
     * @return <code>null</code>
     * @throws URISyntaxException if a URI syntax error has been occurred
     * @throws IOException if an I/O error has occurred
     */
    public String login() throws URISyntaxException, IOException
    {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        HttpServletRequest request =
//                (HttpServletRequest) externalContext.getRequest();
//
//        // Redirects the user agent to the authorization endpoint.
//        String uri = authorizationRequestUri(request);
//        externalContext.redirect(uri);

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
    public int hashCode()
    {
        int code = getClass().hashCode();
        if (bitbucketAPI != null) {
            code ^= bitbucketAPI.hashCode();
        }
        return code;
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
            if (bitbucketAPI != null) {
                if (!bitbucketAPI.equals(other.bitbucketAPI)) {
                    return false;
                }
            }
            else if (bitbucketAPI != other.bitbucketAPI) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUUID()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWebsite()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocation()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant getCreated()
    {
        // @todo Implement this method.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getLinks()
    {
        // @todo Implement this method.
        return null;
    }
}
