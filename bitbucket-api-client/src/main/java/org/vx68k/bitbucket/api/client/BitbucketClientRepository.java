/*
 * BitbucketClientRepository.java - class BitbucketClientRepository
 * Copyright (C) 2015-2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketBranch;
import org.vx68k.bitbucket.api.BitbucketIssue;
import org.vx68k.bitbucket.api.BitbucketRepository;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Repository represented by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientRepository extends BitbucketClientObject
    implements BitbucketRepository
{
    /**
     * Type name for JSON repository objects.
     */
    private static final String REPOSITORY = "repository";

    /**
     * Name for the {@code owner} object in a JSON object.
     */
    private static final String OWNER = "owner";

    /**
     * Name for the {@code uuid} value in a JSON object.
     */
    private static final String UUID = "uuid";

    /**
     * Name for the {@code name} value in a JSON object.
     */
    private static final String NAME = "name";

    /**
     * Name for the {@code full_name} value in a JSON object.
     */
    private static final String FULL_NAME = "full_name";

    /**
     * Name for the {@code scm} value in a JSON object.
     */
    private static final String SCM = "scm";

    /**
     * Name for the {@code mainbranch} value in a JSON object.
     */
    private static final String MAINBRANCH = "mainbranch";

    /**
     * Name for the {@code created_on} value in a JSON object.
     */
    private static final String CREATED_ON = "created_on";

    /**
     * Name for the {@code updated_on} value in a JSON object.
     */
    private static final String UPDATED_ON = "updated_on";

    /**
     * Name for the {@code is_private} value in a JSON object.
     */
    private static final String IS_PRIVATE = "is_private";

    /**
     * Name for the {@code links} object in a JSON object.
     */
    private static final String LINKS = "links";

    /**
     * Constructs this repository with a JSON repository object.
     *
     * @param object JSON repository object
     * @exception IllegalArgumentException if the given JSON object was {@code
     * null} or did not represent a repository
     */
    public BitbucketClientRepository(final JsonObject object)
    {
        super(object);

        String type = getType();
        if (type == null || !type.equals(REPOSITORY)) {
            throw new IllegalArgumentException(
                "JSON object is not repository");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BitbucketUser getOwner()
    {
        JsonObject object = getJsonObject();
        return BitbucketClient.createUser(object.getJsonObject(OWNER));
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
        return BitbucketClientUtilities.parseUUID(
            object.getJsonString(UUID));
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
     */
    @Override
    public final String getSCM()
    {
        JsonObject object = getJsonObject();
        return object.getString(SCM, null);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation takes the object of {@code "mainbranch"} in the
     * underlying JSON object.</p>
     */
    @Override
    public final BitbucketBranch getMainBranch()
    {
        JsonObject object = getJsonObject();
        return BitbucketClient.createBranch(object.getJsonObject(MAINBRANCH));
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
     * <p>This implementation takes the date-time value of {@code "created_on"}
     * in the underlying JSON object.</p>
     */
    @Override
    public Instant getCreated()
    {
        JsonObject object = getJsonObject();
        Instant value = null;
        if (object.containsKey(CREATED_ON)) {
            value = OffsetDateTime.parse(object.getString(CREATED_ON))
                .toInstant();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation gets the date-time value of {@code "updated_on"}
     * in the underlying JSON object.</p>
     */
    @Override
    public Instant getUpdated()
    {
        JsonObject object = getJsonObject();
        Instant value = null;
        if (object.containsKey(UPDATED_ON)) {
            value = OffsetDateTime.parse(object.getString(UPDATED_ON))
                .toInstant();
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<String, String> getLinks()
    {
        JsonObject object = getJsonObject();
        return BitbucketClientUtilities.parseLinks(
            object.getJsonObject(LINKS));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<BitbucketIssue> getIssues()
    {
        // @todo Implemtnt this method.
        return null;
    }
}
