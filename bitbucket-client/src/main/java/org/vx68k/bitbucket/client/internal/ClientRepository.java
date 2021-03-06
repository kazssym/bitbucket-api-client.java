/*
 * ClientRepository.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client.internal;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketProject;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.client.adapter.BitbucketAccountAdapter;
import org.vx68k.bitbucket.client.adapter.UUIDAdapter;

/**
 * Implementation class of {@link BitbucketRepository} for the Bitbucket
 * Cloud REST API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientRepository implements BitbucketRepository
{
    public static final String REPOSITORY = "repository";

    private String type;

    private UUID uuid;

    private String name;

    private String fullName;

    private String description;

    private boolean privateAccess;

    private String forkPolicy;

    private String website;

    private String language;

    private String scm;

    private OffsetDateTime created;

    private OffsetDateTime updated;

    private long size;

    private boolean issuesEnabled;

    private boolean wikiEnabled;

    private ClientAccount owner;

    // workspace

    private ClientProject project;

    private ClientBranch mainBranch;

    /**
     * Constructs a repository.
     */
    public ClientRepository()
    {
        // Nothing to do.
    }

    public ClientRepository(final ClientRepository other)
    {
        this.type = other.type;
        this.uuid = other.uuid;
        this.name = other.name;
        this.fullName = other.fullName;
        this.description = other.description;
        this.privateAccess = other.privateAccess;
        this.forkPolicy = other.forkPolicy;
        this.website = other.website;
        this.language = other.language;
        this.scm = other.scm;
        this.created = other.created;
        this.updated = other.updated;
        this.size = other.size;
        this.issuesEnabled = other.issuesEnabled;
        this.wikiEnabled = other.wikiEnabled;

        setOwner(other.owner);
        setProject(other.project);
        setMainBranch(other.mainBranch);
    }

    /**
     * Returns the type of the repository.
     *
     * @return the type of the repository
     */
    public final String getType()
    {
        return type;
    }

    public final void setType(final String type)
    {
        if (type != null && !(type.equals(REPOSITORY))) {
            throw new IllegalArgumentException("Type is not of repository object");
        }
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @JsonbTypeAdapter(UUIDAdapter.class)
    @Override
    public final UUID getUuid()
    {
        return uuid;
    }

    /**
     * Sets the UUID of the repository.
     *
     * @param uuid a {@link UUID} object
     */
    public final void setUuid(final UUID uuid)
    {
        this.uuid = uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        return name;
    }

    /**
     * Sets the name of the repository.
     *
     * @param name a string object for the name
     */
    public final void setName(final String name)
    {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @JsonbProperty("full_name")
    @Override
    public final String getFullName()
    {
        return fullName;
    }

    /**
     * Sets the full name of the repository.
     *
     * @param fullName a string object for the full name
     */
    @JsonbProperty("full_name")
    public final void setFullName(final String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the string of {@code "description"} in the
     * underlying JSON object.</p>
     */
    @Override
    public final String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the repository.
     *
     * @param description a string object for the description
     */
    public final void setDescription(final String description)
    {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @JsonbProperty("is_private")
    @Override
    public final boolean isPrivateAccess()
    {
        return privateAccess;
    }

    /**
     * Sets the private flag of the repository.
     *
     * @param privateAccess a Boolean value for the private flag
     */
    @JsonbProperty("is_private")
    public final void setPrivate(boolean privateAccess) {
        this.privateAccess = privateAccess;
    }

    @JsonbProperty("fork_policy")
    @Override
    public final String getForkPolicy()
    {
        return forkPolicy;
    }

    @JsonbProperty("fork_policy")
    public final void setForkPolicy(final String forkPolicy)
    {
        this.forkPolicy = forkPolicy;
    }

    @Override
    public final String getWebsite()
    {
        return website;
    }

    public final void setWebsite(final String website)
    {
        this.website = website;
    }

    @Override
    public final String getLanguage()
    {
        return language;
    }

    public final void setLanguage(String language)
    {
        this.language = language;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getScm()
    {
        return scm;
    }

    /**
     * Sets the SCM of the repository.
     *
     * @param scm a string object for the SCM
     */
    public final void setScm(final String scm)
    {
        this.scm = scm;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the date-time value of {@code "created_on"}
     * in the underlying JSON object.</p>
     */
    @JsonbProperty("created_on")
    @Override
    public final OffsetDateTime getCreated()
    {
        return created;
    }

    /**
     * Sets the create time of the repository.
     *
     * @param created a {@link OffsetDateTime} object for the create time
     */
    @JsonbProperty("created_on")
    public final void setCreated(final OffsetDateTime created)
    {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the date-time value of {@code "updated_on"}
     * in the underlying JSON object.</p>
     */
    @JsonbProperty("updated_on")
    @Override
    public final OffsetDateTime getUpdated()
    {
        return updated;
    }

    /**
     * Sets the update time of the repository.
     *
     * @param updated a {@link OffsetDateTime} object for the update time
     */
    @JsonbProperty("updated_on")
    public final void setUpdated(final OffsetDateTime updated)
    {
        this.updated = updated;
    }

    @Override
    public final long getSize()
    {
        return size;
    }

    public final void setSize(final long size)
    {
        this.size = size;
    }

    @JsonbProperty("has_issues")
    @Override
    public final boolean isIssuesEnabled()
    {
        return issuesEnabled;
    }

    @JsonbProperty("has_issues")
    public final void setIssuesEnabled(boolean issuesEnabled)
    {
        this.issuesEnabled = issuesEnabled;
    }

    @JsonbProperty("has_wiki")
    @Override
    public final boolean isWikiEnabled()
    {
        return wikiEnabled;
    }

    @JsonbProperty("has_wiki")
    public final void setWikiEnabled(final boolean wikiEnabled)
    {
        this.wikiEnabled = wikiEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @JsonbTypeAdapter(BitbucketAccountAdapter.class)
    @Override
    public final BitbucketAccount getOwner()
    {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param owner a {@link ClientAccount} object for the owner
     */
    public final void setOwner(ClientAccount owner)
    {
        if (owner != null) {
            owner = owner.copy();
        }
        this.owner = owner;
    }

    @Override
    public final BitbucketProject getProject()
    {
        return project;
    }

    /**
     * Sets the project of the repository.
     * This method provides a hint for the JSON-B runtime to determine the
     * implementation class.
     *
     * @param project a {@link ClientProject} object for the project
     */
    public final void setProject(ClientProject project)
    {
        if (project != null) {
            project = new ClientProject(project);
        }
        this.project = project;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the object of {@code "mainbranch"} in the
     * underlying JSON object.</p>
     */
    @JsonbProperty("mainbranch")
    @Override
    public final BitbucketBranch getMainBranch()
    {
        return mainBranch;
    }

    /**
     * Sets the main branch of the repository.
     * This method provides a hint for the JSON-B runtime to determine the
     * implementation class.
     *
     * @param mainBranch a {@link ClientBranch} object for the main branch
     */
    public final void setMainBranch(ClientBranch mainBranch)
    {
        if (mainBranch != null) {
            mainBranch = new ClientBranch(mainBranch);
        }
        this.mainBranch = mainBranch;
    }

    @Override
    public final String toString()
    {
        if (uuid != null) {
            return "{" + uuid.toString() + "}";
        }

        return super.toString();
    }
}
