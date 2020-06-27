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

package org.vx68k.bitbucket.webapp;

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
 * Request-scoped bean to look up a team name on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@SuppressWarnings({"designForExtension"})
@Named
@RequestScoped
public class TeamInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * User context given to the constructor.
     */
    private final SessionUser sessionUser;

    /**
     * Team name to look up.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "Team name must not contain slashes.")
    private String name = "";

    /**
     * Team found by the last lookup, or {@code null} if no team was found.
     */
    private transient BitbucketAccount team = null;

    /**
     * Constructs this object.
     *
     * @param sessionUser user context
     */
    @Inject
    public TeamInfo(final SessionUser sessionUser)
    {
        this.sessionUser = sessionUser;
    }

    /**
     * Returns the user context given to the constructor.
     *
     * @return the user context
     */
    public SessionUser getSessionUser()
    {
        return sessionUser;
    }

    /**
     * Returns the Bitbucket client of the user context.
     *
     * @return the Bitbucket client
     */
    protected BitbucketClient getBitbucketClient()
    {
        return sessionUser.getBitbucketClient();
    }

    /**
     * Returns the team name to look up.
     *
     * @return the team name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the team name to look up.
     *
     * @param value new value of the team name
     */
    public void setName(final String value)
    {
        name = value;
    }

    /**
     * Returns the team found by the last lookup.
     *
     * @return the team if one was found; {@code null} otherwise
     * @see #isFound
     */
    public BitbucketAccount getTeam()
    {
        return team;
    }

    /**
     * Returns {@code true} if a team was found by the last lookup.
     *
     * @return {@code true} if found; {@code false} otherwise
     */
    public boolean isFound()
    {
        return team != null;
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
            team = bitbucketClient.getTeamAccount(name);
            if (!isFound()) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                UIComponent c = UIComponent.getCurrentComponent(facesContext);
                facesContext.addMessage(c.getClientId(facesContext),
                    new FacesMessage("Team not found."));
            }
        }
        else {
            team = null;
        }
        return null;
    }
}
