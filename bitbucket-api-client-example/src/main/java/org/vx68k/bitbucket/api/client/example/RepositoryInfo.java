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
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * Managed bean for repository name lookup.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@Named
@RequestScoped
public class RepositoryInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * {@link UserContext} object given to the constructor.
     */
    private final UserContext userContext;

    /**
     * Owner name of the repository to look up.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "Owner name must not contain slashes.")
    private String ownerName = "";

    /**
     * Name of the repository to look up.
     */
    @NotNull
    @Pattern(regexp = "[^/]*",
        message = "Repository name must not contain slashes.")
    private String name = "";

    /**
     * Found repository, or {@code null} if not found.
     */
    private transient BitbucketRepository repository = null;

    /**
     * Constructs this object with a {@link UserContext} object.
     *
     * @param context {@link UserContext} object
     */
    @Inject
    public RepositoryInfo(final UserContext context)
    {
        userContext = context;
    }

    /**
     * Returns the {@link UserContext} object given to the constructor.
     *
     * @return the {@link UserContext} object given to the constructor
     */
    protected UserContext getUserContext()
    {
        return userContext;
    }

    /**
     * Returns the {@link BitbucketClient} object from the user context.
     *
     * @return {@link BitbucketClient} object
     */
    protected BitbucketClient getBitbucketClient()
    {
        return userContext.getBitbucketClient();
    }

    /**
     * Returns the owner name of the repository to lookup.
     *
     * @return the owner name of the repository
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * Sets the owner name of the repository to lookup.
     *
     * @param value {@link String} value
     */
    public void setOwnerName(final String value)
    {
        ownerName = value;
    }

    /**
     * Returns the repository name.
     *
     * @return the repository name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the repository name to a {@link String} value.
     *
     * @param value {@link String} value
     */
    public void setName(final String value)
    {
        name = value;
    }

    /**
     * Returns the found repository.
     *
     * @return the found repository, or {@code null} if not found
     */
    public BitbucketRepository getRepository()
    {
        return repository;
    }

    /**
     * Returns {@code true} if a repository was found.
     *
     * @return {@code true} if a repository was found
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
