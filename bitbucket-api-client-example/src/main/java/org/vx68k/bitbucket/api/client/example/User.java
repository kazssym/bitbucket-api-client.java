/*
 * User.java - class User
 * Copyright (C) 2018 Kaz Nishimura
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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * Bitbucket user information.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@RequestScoped
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * {@link CurrentUser} object given to the constructor.
     */
    private final CurrentUser currentUser;

    /**
     * User name.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "User name must not contain slashes.")
    private String name = "";

    /**
     * Found {@link BitbucketUser} object.
     */
    private BitbucketUser bitbucketUser = null;

    /**
     * Constructs this object with no {@link CurrentUser} object.
     */
    public User()
    {
        this(null);
    }

    /**
     * Constructs this object with a {@link CurrentUser} object.
     *
     * @param aCurrentUser {@link CurrentUser} object
     */
    @Inject
    public User(final CurrentUser aCurrentUser)
    {
        currentUser = aCurrentUser;
    }

    /**
     * Returns the {@link CurrentUser} object given to the constructor.
     *
     * @return the {@link CurrentUser} object given to the constructor
     */
    public CurrentUser getCurrentUser()
    {
        return currentUser;
    }

    /**
     * Returns the user name.
     *
     * @return the user name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the user name to a {@link String} value.
     *
     * @param value {@link String} value for the user name
     */
    public void setName(final String value)
    {
        name = value;
    }

    /**
     * Returns the found {@link BitbucietUser} object.
     *
     * @return the found {@link BitbucietUser} object
     */
    public BitbucketUser getBitbucketUser()
    {
        return bitbucketUser;
    }

    /**
     * Fetches the user object to view.
     *
     * @return {@code null}
     */
    public Object view()
    {
        if (!name.isEmpty()) {
            bitbucketUser = BitbucketClient.getUser(name);
        }
        else {
            bitbucketUser = null;
        }
        return null;
    }
}
