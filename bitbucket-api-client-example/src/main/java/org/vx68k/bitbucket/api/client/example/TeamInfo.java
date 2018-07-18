/*
 * TeamInfo.java - class TeamInfo
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
 * Managed bean for team name lookup.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@Named
@RequestScoped
public class TeamInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * {@link UserContext} object given to the constructor.
     */
    private final UserContext userContext;

    /**
     * User name.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "Team name must not contain slashes.")
    private String name = "";

    /**
     * {@link BitbucketUser} object of the found user, or {@code null} if no
     * user was found.
     */
    private transient BitbucketUser foundTeam = null;

    /**
     * Constructs this object with a {@link UserContext} object.
     *
     * @param context {@link UserContext} object
     */
    @Inject
    public TeamInfo(final UserContext context)
    {
        userContext = context;
    }

    /**
     * Returns the {@link UserContext} object given to the constructor.
     *
     * @return the {@link UserContext} object given to the constructor
     */
    public UserContext getUserContext()
    {
        return userContext;
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
     * Returns the {@link BitbucketUser} object of the found user.
     *
     * @return the {@link BitbucketUser} object of the found user
     */
    public BitbucketUser getFoundTeam()
    {
        return foundTeam;
    }

    /**
     * Returns {@code true} if a user was found.
     *
     * @return {@code true} if a user was found
     */
    public boolean isFound()
    {
        return foundTeam != null;
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
            foundTeam = userContext.getBitbucketClient().getTeam(name);
            if (!isFound()) {
                facesContext.addMessage(component.getClientId(facesContext),
                    new FacesMessage("Team not found."));
            }
        }
        else {
            foundTeam = null;
        }
        return null;
    }
}
