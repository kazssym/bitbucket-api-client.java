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
 * {@code repository} type.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientRepository implements BitbucketRepository,
    BitbucketIssueTracker // TODO: Change this class to package scope.
{
    /**
     * Name of the {@code fork_policy} value in a JSON repository object.
     */
    private static final String FORK_POLICY = "fork_policy";

    /**
     * Name of the {@code language} value in a JSON repository object.
     */
    private static final String LANGUAGE = "language";

    /**
     * Name of the {@code mainbranch} value in a JSON repository object.
     */
    private static final String MAINBRANCH = "mainbranch";

    /**
     * Name of the {@code project} value in a JSON repository object.
     */
    private static final String PROJECT = "project";

    /**
     * Name of the {@code website} value in a JSON repository object.
     */
    private static final String WEBSITE = "website";

    @JsonbProperty("owner")
    private BitbucketAccount owner;

    @JsonbProperty("uuid")
    private UUID uuid;

    @JsonbProperty("name")
    private String name;

    @JsonbProperty("full_name")
    private String fullName;

    @JsonbProperty("description")
    private String description;

    @JsonbProperty("is_private")
    private boolean restricted;


    @JsonbProperty("scm")
    private String scm;

    @JsonbProperty("created_on")
    private Instant created;

    @JsonbProperty("updated_on")
    private Instant updated;

    @JsonbProperty("size")
    private long size;

    @JsonbProperty("has_issues")
    private boolean issuesEnabled;

    @JsonbProperty("has_wiki")
    private boolean wikiEnabled;

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

        this.scm = other.scm;
        this.created = other.created;
        this.updated = other.updated;
        this.size = other.size;
        this.issuesEnabled = other.issuesEnabled;
        this.wikiEnabled = other.wikiEnabled;
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
    public final UUID getUUID()
    {
        return uuid;
    }

    /**
     * Sets the UUID of the repository.
     *
     * @param uuid a {@link UUID} object
     */
    public final void setUUID(final UUID uuid)
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
    public final void setPrivate(boolean restricted) {
        this.restricted = restricted;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the object of {@code "mainbranch"} in the
     * underlying JSON object.</p>
     */
    @Override
    public final BitbucketBranch getMainBranch()
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getSCM()
    {
        return scm;
    }

    /**
     * Sets the SCM of the repository.
     *
     * @param scm a string object for the SCM
     */
    public final void setSCM(final String scm)
    {
        this.scm = scm;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the date-time value of {@code "created_on"}
     * in the underlying JSON object.</p>
     */
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
    public final void setCreated(final Instant created)
    {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the date-time value of {@code "updated_on"}
     * in the underlying JSON object.</p>
     */
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

    @Override
    public final boolean hasIssueTracker()
    {
        return issuesEnabled;
    }

    @Override
    public final boolean hasWiki()
    {
        return wikiEnabled;
    }

    @Deprecated
    @Override
    public final BitbucketIssueTracker getIssueTracker()
    {
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Deprecated
    @Override
    public final BitbucketRepository getRepository()
    {
        throw new UnsupportedOperationException("Unimplemented");
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    @Override
    public final BitbucketIssue getIssue(final int id)
    {
        throw new UnsupportedOperationException("Unimplemented");
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated
    @Override
    public final Collection<BitbucketIssue> issues()
    {
        throw new UnsupportedOperationException("Unimplemented");
    }

    @Deprecated
    @Override
    public final Collection<BitbucketIssue> issues(final String filter)
    {
        throw new UnsupportedOperationException("Unimplemented");
    }
}
