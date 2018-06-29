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
import javax.inject.Inject;
import javax.inject.Named;
import org.vx68k.bitbucket.api.BitbucketRepository;
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
     * {@link BitbucketUser} object for the current user, or {@code null} if
     * not logged in.
     */
    private BitbucketUser loggedInUser = null;

    /**
     * Constructs this object with no {@link BitbucketAPI} object.
     */
    public CurrentUser()
    {
        this(null);
    }

    /**
     * Constructs this object with a {@link BitbucketAPI} object.
     *
     * @param aBitbucketAPI {@link BitbucketAPI object}
     */
    @Inject
    public CurrentUser(final BitbucketAPI aBitbucketAPI)
    {
        bitbucketAPI = aBitbucketAPI;
    }

    /**
     * Returns the {@link BitbucketAPI} object given to the constructor.
     *
     * @return the {@link BitbucketAPI} object given to the constructor
     */
    public BitbucketAPI getBitbucketAPI()
    {
        return bitbucketAPI;
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
        String type = null;
        if (loggedInUser != null) {
            type = loggedInUser.getType();
        }
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        String name = null;
        if (loggedInUser != null) {
            name = loggedInUser.getName();
        }
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUUID()
    {
        UUID uuid = null;
        if (loggedInUser != null) {
            uuid = loggedInUser.getUUID();
        }
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName()
    {
        String displayName = null;
        if (loggedInUser != null) {
            displayName = loggedInUser.getDisplayName();
        }
        return displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWebsite()
    {
        String website = null;
        if (loggedInUser != null) {
            website = loggedInUser.getWebsite();
        }
        return website;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocation()
    {
        String location = null;
        if (loggedInUser != null) {
            location = loggedInUser.getLocation();
        }
        return location;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPrivate()
    {
        boolean private_ = false;
        if (loggedInUser != null) {
            private_ = loggedInUser.isPrivate();
        }
        return private_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant getCreated()
    {
        Instant created = null;
        if (loggedInUser != null) {
            created = loggedInUser.getCreated();
        }
        return created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getLinks()
    {
        Map<String, String> links = null;
        if (loggedInUser != null) {
            links = loggedInUser.getLinks();
        }
        return links;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BitbucketRepository> repositorySet()
    {
        Set<BitbucketRepository> set = null;
        if (loggedInUser != null) {
            set = loggedInUser.repositorySet();
        }
        return set;
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
}
