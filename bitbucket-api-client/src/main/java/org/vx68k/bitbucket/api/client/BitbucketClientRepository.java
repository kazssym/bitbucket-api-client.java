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

import java.util.Map;
import java.util.UUID;
import javax.json.JsonObject;
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
     * Name for the {@code owner} object in a JSON user object.
     */
    private static final String OWNER = "owner";

    /**
     * Name for the {@code uuid} value in a JSON user object.
     */
    private static final String UUID = "uuid";

    /**
     * Name for the {@code name} value in a JSON user object.
     */
    private static final String NAME = "name";

    /**
     * Name for the {@code full_name} value in a JSON user object.
     */
    private static final String FULL_NAME = "full_name";

    /**
     * Name for the {@code scm} value in a JSON user object.
     */
    private static final String SCM = "scm";

    /**
     * Name for the {@code is_private} value in a JSON user object.
     */
    private static final String IS_PRIVATE = "is_private";

    /**
     * Name for the {@code links} object in a JSON user object.
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
     */
    @Override
    public final boolean isPrivate()
    {
        JsonObject object = getJsonObject();
        return object.getBoolean(IS_PRIVATE, false);
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
}
