/*
 * UserInfo.java - class UserInfo
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

package org.vx68k.bitbucket.api.client.webapp;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * Request-scoped bean to look up a user name on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@SuppressWarnings({"designForExtension"})
@Named
@RequestScoped
public class UserInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * User context given to the constructor.
     */
    private final UserContext userContext;

    /**
     * User name to look up.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "User name must not contain slashes.")
    private String name = "";

    /**
     * User found by the last lookup, or {@code null} if no user was found.
     */
    private transient BitbucketAccount user = null;

    /**
     * Constructs this object.
     *
     * @param context user context
     */
    @Inject
    public UserInfo(final UserContext context)
    {
        userContext = context;
    }

    /**
     * Returns the user context given to the constructor.
     *
     * @return the user context
     */
    public UserContext getUserContext()
    {
        return userContext;
    }

    /**
     * Returns the Bitbucket client of the user context.
     *
     * @return the Bitbucket client
     */
    protected BitbucketClient getBitbucketClient()
    {
        return userContext.getBitbucketClient();
    }

    /**
     * Returns the user name to look up.
     *
     * @return the user name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the user name to look up.
     *
     * @param value new value of the user name
     */
    public void setName(final String value)
    {
        name = value;
    }

    /**
     * Returns the user found by the last lookup.
     *
     * @return the user if one was found; {@code null} otherwise
     * @see #isFound
     */
    public BitbucketAccount getUser()
    {
        return user;
    }

    /**
     * Returns {@code true} if a user was found by the last lookup.
     *
     * @return {@code true} if found; {@code false} otherwise
     */
    public boolean isFound()
    {
        return user != null;
    }

    /**
     * Performs an action to look up.
     * <p>This method always returns {@code null}.</p>
     *
     * @return {@code null}
     */
    public Object lookUp()
    {
        if (!name.isEmpty()) {
            BitbucketClient bitbucketClient = getBitbucketClient();
            user = bitbucketClient.getUser(name);
            if (!isFound()) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                UIComponent c = UIComponent.getCurrentComponent(facesContext);
                facesContext.addMessage(c.getClientId(facesContext),
                    new FacesMessage("User not found."));
            }
        }
        else {
            user = null;
        }
        return null;
    }
}
