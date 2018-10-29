/*
 * RepositoryInfo.java - class RepositoryInfo
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
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * Request-scoped bean to look up a repository name on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@SuppressWarnings({"designForExtension"})
@Named
@RequestScoped
public class RepositoryInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * User context given to the constructor.
     */
    private final UserContext userContext;

    /**
     * Owner name to look up.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "Owner name must not contain slashes.")
    private String ownerName = "";

    /**
     * Repository name to look up.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "Repository name must not contain slashes.")
    private String name = "";

    /**
     * Repository found by the last lookup, or {@code null} if no repository
     * was found.
     */
    private transient BitbucketRepository repository = null;

    /**
     * Constructs this object.
     *
     * @param context user context
     */
    @Inject
    public RepositoryInfo(final UserContext context)
    {
        userContext = context;
    }

    /**
     * Returns the user context given to the constructor.
     *
     * @return the user context
     */
    protected UserContext getUserContext()
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
     * Returns the owner name to look up.
     *
     * @return the owner name
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * Sets the owner name to look up.
     *
     * @param value new value of the owner name
     */
    public void setOwnerName(final String value)
    {
        ownerName = value;
    }

    /**
     * Returns the repository name to look up.
     *
     * @return the repository name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the repository name to look up.
     *
     * @param value new value of the repository name
     */
    public void setName(final String value)
    {
        name = value;
    }

    /**
     * Returns the repository found by the last lookup.
     *
     * @return the repository if one was found; {@code null} otherwise
     * @see #isFound
     */
    public BitbucketRepository getRepository()
    {
        return repository;
    }

    /**
     * Returns {@code true} if a repository was found by the last lookup.
     *
     * @return {@code true} if found; {@code false} otherwise
     */
    public boolean isFound()
    {
        return repository != null;
    }

    /**
     * Performs an action to look up.
     * <p>This method always returns {@code null}.</p>
     *
     * @return {@code null}
     */
    public String lookUp()
    {
        if (!ownerName.isEmpty() && !name.isEmpty()) {
            BitbucketClient bitbucketClient = getBitbucketClient();
            repository = bitbucketClient.getRepository(ownerName, name);
            if (!isFound()) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                UIComponent c = UIComponent.getCurrentComponent(facesContext);
                facesContext.addMessage(c.getClientId(facesContext),
                    new FacesMessage("Repository not found."));
            }
        }
        else {
            repository = null;
        }
        return null;
    }
}
