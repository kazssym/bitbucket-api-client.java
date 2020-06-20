/*
 * ClientRepository.java
 * Copyright (C) 2015-2020 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.vx68k.bitbucket.client.internal;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Client implementation class of {@link BitbucketRepository} for the
 * {@code "repository"} type.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientRepository implements BitbucketRepository
{
    @JsonbTypeAdapter(UUIDAdapter.class)
    private UUID uuid;

    private String name;

    private String fullName;

    private String description;

    private boolean restricted;

    private String forkPolicy;

    private String website;

    private String language;

    private String scm;

    private OffsetDateTime created;

    private OffsetDateTime updated;

    private long size;

    private boolean issuesEnabled;

    private boolean wikiEnabled;

    private BitbucketAccount owner;

    // workspace

    // project

    private ClientBranch mainBranch;

    /**
     * Constructs a repository.
     */
    public ClientRepository()
    {
        // Nothing to do.
    }

    public ClientRepository(final BitbucketRepository other)
    {
        this.uuid = other.getUuid();
        this.name = other.getName();
        this.fullName = other.getFullName();
        this.description = other.getDescription();
        this.restricted = other.isPrivate();
        this.forkPolicy = other.getForkPolicy();
        this.website = other.getWebsite();
        this.language = other.getLanguage();
        this.scm = other.getScm();
        this.created = other.getCreated();
        this.updated = other.getUpdated();
        this.size = other.getSize();
        this.issuesEnabled = other.hasIssueTracker();
        this.wikiEnabled = other.hasWiki();

        this.owner = other.getOwner(); // TODO: Make a copy.
        this.mainBranch = new ClientBranch(other.getMainBranch());
    }

    public final String getType()
    {
        return "repository";
    }

    /**
     * {@inheritDoc}
     */
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
    public final boolean isPrivate()
    {
        return restricted;
    }

    /**
     * Sets the private flag of the repository.
     *
     * @param restricted a Boolean value for the private flag
     */
    @JsonbProperty("is_private")
    public final void setPrivate(boolean restricted) {
        this.restricted = restricted;
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
    public final boolean hasIssueTracker()
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
    public final boolean hasWiki()
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
    @Override
    public final BitbucketAccount getOwner()
    {
        return owner;
    }

    /**
     * Sets the owner of the repository.
     *
     * @param owner a {@link ClientAccount} object for the owner
     */
    public final void setOwner(final BitbucketAccount owner)
    {
        this.owner = owner; // TODO: Make a copy.
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the object of {@code "mainbranch"} in the
     * underlying JSON object.</p>
     */
    @JsonbProperty("mainbranch")
    @Override
    public final ClientBranch getMainBranch()
    {
        return mainBranch;
    }

    @JsonbProperty("mainbranch")
    public final void setMainBranch(final ClientBranch mainBranch)
    {
        this.mainBranch = new ClientBranch(mainBranch);
    }
}
