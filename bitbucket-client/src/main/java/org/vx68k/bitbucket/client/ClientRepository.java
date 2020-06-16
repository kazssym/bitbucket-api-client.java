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

package org.vx68k.bitbucket.client;

import static org.vx68k.bitbucket.client.JsonUtilities.toInstant;
import static org.vx68k.bitbucket.client.JsonUtilities.toUUID;
import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbProperty;
import javax.ws.rs.core.Link;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketBranch;
import org.vx68k.bitbucket.BitbucketIssue;
import org.vx68k.bitbucket.BitbucketIssueTracker;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Client implementation of {@link BitbucketRepository}.
 *
 * @author Kaz Nishimura
 * @since 6.0.0
 */
public class ClientRepository implements BitbucketRepository,
    BitbucketIssueTracker // TODO: Change this class to package scope.
{
    /**
     * Type value for repositories.
     */
    private static final String REPOSITORY = "repository";

    private static final String JSON_TYPE = "type";

    /**
     * Name of the {@code owner} value in a JSON repository object.
     */
    private static final String JSON_OWNER = "owner";

    /**
     * Name of the {@code uuid} value in a JSON repository object.
     */
    private static final String JSON_UUID = "uuid";

    /**
     * Name of the {@code name} value in a JSON repository object.
     */
    private static final String JSON_NAME = "name";

    /**
     * Name of the {@code full_name} value in a JSON repository object.
     */
    private static final String JSON_FULL_NAME = "full_name";

    /**
     * Name of the {@code description} value in a JSON repository object.
     */
    private static final String JSON_DESCRIPTION = "description";

    /**
     * Name of the {@code is_private} value in a JSON repository object.
     */
    private static final String JSON_PRIVATE = "is_private";

    /**
     * Name of the {@code scm} value in a JSON repository object.
     */
    private static final String JSON_SCM = "scm";

    /**
     * Name of the {@code created_on} value in a JSON repository object.
     */
    private static final String JSON_CREATED = "created_on";

    /**
     * Name of the {@code updated_on} value in a JSON repository object.
     */
    private static final String JSON_UPDATED = "updated_on";

    /**
     * Name of the {@code size} value in a JSON repository object.
     */
    private static final String JSON_SIZE = "size";

    /**
     * Name of the {@code has_issues} value in a JSON repository object.
     */
    private static final String JSON_ISSUES_ENABLED = "has_issues";

    /**
     * Name of the {@code has_wiki} value in a JSON repository object.
     */
    private static final String JSON_WIKI_ENABLED = "has_wiki";

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

    @JsonbProperty(JSON_OWNER)
    private BitbucketAccount owner;

    @JsonbProperty(JSON_UUID)
    private UUID uuid;

    @JsonbProperty(JSON_NAME)
    private String name;

    @JsonbProperty(JSON_FULL_NAME)
    private String fullName;

    @JsonbProperty(JSON_DESCRIPTION)
    private String description;

    @JsonbProperty(JSON_PRIVATE)
    private boolean restricted;


    @JsonbProperty(JSON_SCM)
    private String scm;

    @JsonbProperty(JSON_CREATED)
    private Instant created;

    @JsonbProperty(JSON_UPDATED)
    private Instant updated;

    @JsonbProperty(JSON_SIZE)
    private long size;

    @JsonbProperty(JSON_ISSUES_ENABLED)
    private boolean issuesEnabled;

    @JsonbProperty(JSON_WIKI_ENABLED)
    private boolean wikiEnabled;

    /**
     * Returns a function to create a repository from a JSON object.
     * <p>This method can be used to create a {@link PaginatedList} object.</p>
     *
     * @return a function to create a repository from a JSON object
     * @see PaginatedList
     */
    public static Function<JsonObject, ClientRepository> creator()
    {
        return ClientRepository::new;
    }

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
     * Constructs a repository from a JSON object.
     *
     * @param json a JSON object
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not of a repository
     */
    public ClientRepository(final JsonObject json)
    {
        if (json == null) {
            throw new IllegalArgumentException("JSON object is null");
        }

        String type = json.getString(JSON_TYPE, null);
        if (!Objects.equals(type, "repository")) {
            throw new IllegalArgumentException("JSON object is not of a repository");
        }

        JsonObject jsonOwner = json.getJsonObject(JSON_OWNER);
        if (jsonOwner == null) {
            this.owner = null;
        }
        else if (Objects.equals(jsonOwner.getString(JSON_TYPE, null),
            BitbucketAccount.AccountType.TEAM.toString())) {
            this.owner = new ClientTeamAccount(jsonOwner);
        }
        else {
            this.owner = new ClientUserAccount(jsonOwner);
        }
        this.uuid = JsonUtilities.toUUID(json.get(JSON_UUID));
        this.name = json.getString(JSON_NAME, null);
        this.fullName = json.getString(JSON_FULL_NAME, null);
        this.description = json.getString(JSON_DESCRIPTION, null);
        this.restricted = json.getBoolean(JSON_PRIVATE, false);

        this.scm = json.getString(JSON_SCM, null);
        this.created = JsonUtilities.toInstant(json.get(JSON_CREATED));
        this.updated = JsonUtilities.toInstant(json.get(JSON_UPDATED));
        this.size = json.getJsonNumber(JSON_SIZE).longValue();
        this.issuesEnabled = json.getBoolean(JSON_ISSUES_ENABLED, false);
        this.wikiEnabled = json.getBoolean(JSON_WIKI_ENABLED, false);
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
