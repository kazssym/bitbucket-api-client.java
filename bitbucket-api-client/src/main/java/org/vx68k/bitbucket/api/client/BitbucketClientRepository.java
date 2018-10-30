/*
 * BitbucketClientRepository.java
 * Copyright (C) 2015-2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client;

import static org.vx68k.bitbucket.api.client.JsonUtilities.toInstant;
import static org.vx68k.bitbucket.api.client.JsonUtilities.toUUID;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.ws.rs.core.Link;
import org.vx68k.bitbucket.api.BitbucketAccount;
import org.vx68k.bitbucket.api.BitbucketBranch;
import org.vx68k.bitbucket.api.BitbucketIssue;
import org.vx68k.bitbucket.api.BitbucketIssueTracker;
import org.vx68k.bitbucket.api.BitbucketRepository;

/**
 * Client implementation of {@link BitbucketRepository}.
 * This class represents a repository by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientRepository extends BitbucketClientObject implements
    BitbucketRepository, BitbucketIssueTracker
{
    /**
     * Type value for repositories.
     */
    private static final String REPOSITORY = "repository";

    /**
     * Name of the {@code created_on} value in a JSON repository object.
     */
    private static final String CREATED_ON = "created_on";

    /**
     * Name of the {@code description} value in a JSON repository object.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Name of the {@code fork_policy} value in a JSON repository object.
     */
    private static final String FORK_POLICY = "fork_policy";

    /**
     * Name of the {@code full_name} value in a JSON repository object.
     */
    private static final String FULL_NAME = "full_name";

    /**
     * Name of the {@code has_issues} value in a JSON repository object.
     */
    private static final String HAS_ISSUES = "has_issues";

    /**
     * Name of the {@code has_wiki} value in a JSON repository object.
     */
    private static final String HAS_WIKI = "has_wiki";

    /**
     * Name of the {@code is_private} value in a JSON repository object.
     */
    private static final String IS_PRIVATE = "is_private";

    /**
     * Name of the {@code language} value in a JSON repository object.
     */
    private static final String LANGUAGE = "language";

    /**
     * Name of the {@code mainbranch} value in a JSON repository object.
     */
    private static final String MAINBRANCH = "mainbranch";

    /**
     * Name of the {@code name} value in a JSON repository object.
     */
    private static final String NAME = "name";

    /**
     * Name of the {@code owner} value in a JSON repository object.
     */
    private static final String OWNER = "owner";

    /**
     * Name of the {@code project} value in a JSON repository object.
     */
    private static final String PROJECT = "project";

    /**
     * Name of the {@code scm} value in a JSON repository object.
     */
    private static final String SCM = "scm";

    /**
     * Name of the {@code size} value in a JSON repository object.
     */
    private static final String SIZE = "size";

    /**
     * Name of the {@code updated_on} value in a JSON repository object.
     */
    private static final String UPDATED_ON = "updated_on";

    /**
     * Name of the {@code uuid} value in a JSON repository object.
     */
    private static final String UUID = "uuid";

    /**
     * Name of the {@code website} value in a JSON repository object.
     */
    private static final String WEBSITE = "website";

    /**
     * Constructs this object.
     *
     * @param object JSON object for a repository
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not for a repository
     */
    public BitbucketClientRepository(final JsonObject object)
    {
        this(object, null);
    }

    /**
     * Constructs this object with a Bitbucket client.
     *
     * @param object JSON object for a repository
     * @param client Bitbucket client
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not for a repository
     */
    public BitbucketClientRepository(
        final JsonObject object, final BitbucketClient client)
    {
        super(object, client);

        String type = getType();
        if (type == null || !type.equals(REPOSITORY)) {
            throw new IllegalArgumentException("Not repository");
        }
    }

    /**
     * Returns a function to create a repository from a JSON object.
     * The returned function initializes the Bitbucket API client of the
     * created repository to {@code null}.
     * <p>This method can be used to create a {@link PaginatedList} object.</p>
     *
     * @return a function to create a repository from a JSON object
     * @see #creator(BitbucketClient)
     * @see PaginatedList
     */
    public static Function<JsonObject, BitbucketClientRepository> creator()
    {
        return creator(null);
    }

    /**
     * Returns a function to create a repository from a JSON object.
     * <p>This method can be used to create a {@link PaginatedList} object.</p>
     *
     * @param bitbucketClient a Bitbucket API client
     * @return a function to create a repository from a JSON object
     * @see PaginatedList
     */
    public static Function<JsonObject, BitbucketClientRepository> creator(
        final BitbucketClient bitbucketClient)
    {
        return (object) ->
            new BitbucketClientRepository(object, bitbucketClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getSCM()
    {
        JsonObject object = getJsonObject();
        return object.getString(SCM, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BitbucketAccount getOwner()
    {
        JsonObject object = getJsonObject();
        BitbucketAccount value = null;
        JsonObject owner = object.getJsonObject(OWNER);
        if (owner != null) {
            BitbucketClient client = getBitbucketClient();
            String type = owner.getString(TYPE);
            if (type.equals(BitbucketAccount.TEAM)) {
                value = new BitbucketClientAccount(owner, client);
            }
            else {
                value = new BitbucketClientUser(owner, client);
            }
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        JsonObject object = getJsonObject();
        return object.getString(NAME, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final UUID getUUID()
    {
        JsonObject object = getJsonObject();
        return toUUID(object.getJsonString(UUID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getFullName()
    {
        JsonObject object = getJsonObject();
        return object.getString(FULL_NAME, null);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the string of {@code "description"} in the
     * underlying JSON object.</p>
     */
    @Override
    public final String getDescription()
    {
        JsonObject object = getJsonObject();
        return object.getString(DESCRIPTION, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isPrivate()
    {
        JsonObject object = getJsonObject();
        return object.getBoolean(IS_PRIVATE, false);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the object of {@code "mainbranch"} in the
     * underlying JSON object.</p>
     */
    @Override
    public final BitbucketBranch getMainBranch()
    {
        JsonObject branch = getJsonObject().getJsonObject(MAINBRANCH);

        BitbucketBranch value = null;
        if (branch != null) {
            value = new BitbucketClientBranch(branch, getBitbucketClient());
        }
        return value;
    }

    @Override
    public final boolean hasIssueTracker()
    {
        return getJsonObject().getBoolean(HAS_ISSUES, false);
    }

    @Override
    public final boolean hasWiki()
    {
        return getJsonObject().getBoolean(HAS_WIKI, false);
    }

    @Override
    public final long getSize()
    {
        JsonNumber size = getJsonObject().getJsonNumber(SIZE);

        long value = -1L;
        if (size != null) {
            value = size.longValue();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the date-time value of {@code "created_on"}
     * in the underlying JSON object.</p>
     */
    @Override
    public final Instant getCreated()
    {
        JsonObject object = getJsonObject();
        // This may be a JSON null.
        Instant value = null;
        if (object.containsKey(CREATED_ON) && !object.isNull(CREATED_ON)) {
            value = toInstant(object.getJsonString(CREATED_ON));
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the date-time value of {@code "updated_on"}
     * in the underlying JSON object.</p>
     */
    @Override
    public final Instant getUpdated()
    {
        JsonObject object = getJsonObject();
        // This may be a JSON null.
        Instant value = null;
        if (object.containsKey(UPDATED_ON) && !object.isNull(UPDATED_ON)) {
            value = toInstant(object.getJsonString(UPDATED_ON));
        }
        return value;
    }

    @Override
    public final BitbucketIssueTracker getIssueTracker()
    {
        BitbucketIssueTracker value = null;
        if (hasIssueTracker()) {
            value = this;
        }
        return value;
    }

    @Override
    public final BitbucketRepository getRepository()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BitbucketIssue getIssue(final int id)
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("owner", getOwner().getName());
        parameters.put("name", getName());
        parameters.put("id", id);

        BitbucketClient client = getBitbucketClient();
        JsonObject object = client.getResource(
            "/repositories/{owner}/{name}/issues/{id}", parameters);

        BitbucketIssue value = null;
        if (object != null) {
            value = new BitbucketClientIssue(object, client);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<BitbucketIssue> issues()
    {
        return issues(null);
    }

    @Override
    public final Collection<BitbucketIssue> issues(final String filter)
    {

        Link issues = getLink("issues");
        URI uri;
        if (filter != null) {
            uri = issues.getUriBuilder().queryParam("q", filter).build();
        }
        else {
            uri = issues.getUri();
        }

        BitbucketClient client = getBitbucketClient();
        return new PaginatedList<>(
            uri, client, BitbucketClientIssue.creator(client));
    }
}
