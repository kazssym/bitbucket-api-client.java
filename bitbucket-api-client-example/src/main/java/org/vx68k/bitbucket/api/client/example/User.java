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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Managed bean for user name lookup.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@RequestScoped
@Named
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
     * {@link BitbucketUser} object of the found user, or {@code null} if no
     * user was found.
     */
    private BitbucketUser foundUser = null;

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
     * @param value {@link String} value
     */
    public void setName(final String value)
    {
        name = value;
    }

    /**
     * Returns {@code true} if a user was found.
     *
     * @return {@code true} if a user was found
     */
    public boolean isFound()
    {
        return foundUser != null;
    }

    /**
     * Returns the {@link BitbucietUser} object of the found user.
     *
     * @return the {@link BitbucietUser} object of the found user
     */
    public BitbucketUser getFoundUser()
    {
        return foundUser;
    }

    /**
     * Performs an action to look up the user name.
     *
     * @return {@code null}
     */
    public Object lookUp()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(facesContext);
        if (!name.isEmpty()) {
            foundUser = currentUser.getBitbucketClient().getUser(name);
            if (!isFound()) {
                facesContext.addMessage(component.getClientId(facesContext),
                    new FacesMessage("User not found."));
            }
        }
        else {
            foundUser = null;
        }
        return null;
    }
}
