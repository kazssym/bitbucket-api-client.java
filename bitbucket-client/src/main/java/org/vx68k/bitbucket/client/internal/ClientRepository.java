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

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketIssue;
import org.vx68k.bitbucket.BitbucketIssueTracker;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Client implementation class of {@link BitbucketRepository} for the
 * {@code "repository"} type.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientRepository implements BitbucketRepository, BitbucketIssueTracker
{
    /**
     * Name of the {@code fork_policy} value in a JSON repository object.
     */
    private static final String FORK_POLICY = "fork_policy";

    private BitbucketAccount owner;

    private UUID uuid;

    private String name;

    private String fullName;

    private String description;

    private boolean restricted;

    private String forkPolicy;

    private String website;

    private String language;


    private String scm;

    private Instant created;

    private Instant updated;

    private long size;

    private boolean issuesEnabled;

    private boolean wikiEnabled;

    // project

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
        this.owner = other.owner;
        this.uuid = other.uuid;
        this.name = other.name;
        this.fullName = other.fullName;
        this.description = other.description;
        this.restricted = other.restricted;
        this.website = other.website;
        this.language = other.language;

        this.scm = other.scm;
        this.created = other.created;
        this.updated = other.updated;
        this.size = other.size;
        this.issuesEnabled = other.issuesEnabled;
        this.wikiEnabled = other.wikiEnabled;

        this.mainBranch = new ClientBranch(other.mainBranch);
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
     * @param owner a {@link BitbucketAccount} object for the owner
     */
    public final void setOwner(final BitbucketAccount owner)
    {
        this.owner = owner;
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
    public final String getForkPolicy()
    {
        return forkPolicy;
    }

    @JsonbProperty("fork_policy")
    public final void setForkPolicy(final String forkPolicy)
    {
        this.forkPolicy = forkPolicy;
    }

    public final String getWebsite()
    {
        return website;
    }

    public final void setWebsite(final String website)
    {
        this.website = website;
    }

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
    public final Instant getCreated()
    {
        return created;
    }

    /**
     * Sets the create time of the repository.
     *
     * @param created a {@link Instant} object for the create time
     */
    @JsonbProperty("created_on")
    public final void setCreated(final Instant created)
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
    public final Instant getUpdated()
    {
        return updated;
    }

    /**
     * Sets the update time of the repository.
     *
     * @param updated a {@link Instant} object for the update time
     */
    @JsonbProperty("updated_on")
    public final void setUpdated(final Instant updated)
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

    @JsonbProperty("has_wiki")
    @Override
    public final boolean hasWiki()
    {
        return wikiEnabled;
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

    public final void setMainBranch(final ClientBranch mainBranch)
    {
        this.mainBranch = new ClientBranch(mainBranch);
    }


    @Deprecated
    @Override
    public final BitbucketIssueTracker getIssueTracker()
    {
        throw new UnsupportedOperationException("getIssueTracker is unimplemented");
    }

    @Deprecated
    @Override
    public final BitbucketRepository getRepository()
    {
        throw new UnsupportedOperationException("getRepository is unimplemented");
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    @Override
    public final BitbucketIssue getIssue(final int id)
    {
        throw new UnsupportedOperationException("getIssue is unimplemented");
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    @Override
    public final Collection<BitbucketIssue> issues()
    {
        throw new UnsupportedOperationException("issues is unimplemented");
    }

    @Deprecated
    @Override
    public final Collection<BitbucketIssue> issues(final String filter)
    {
        throw new UnsupportedOperationException("issues is unimplemented");
    }
}
