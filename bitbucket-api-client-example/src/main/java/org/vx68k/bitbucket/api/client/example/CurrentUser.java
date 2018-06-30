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

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.BitbucketClient;

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
     * {@link BitbucketClient} object.
     */
    private final BitbucketClient bitbucketClient;

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
     * Returns {@code true} if the current user is logged in.
     *
     * @return {@code true} if logged in, or {@code false} otherwise
     */
    public boolean isLoggedIn()
    {
        return loggedInUser != null;
    }

    /**
     * Performs a login action by redirecting the user agent to the
     * authorization endpoint.
     *
     * @return {@code null}
     */
    public String login()
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
        }
        return true;
    }
}
