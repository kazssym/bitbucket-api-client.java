/*
 * BitbucketClientAccount.java
 * Copyright (C) 2018 Kaz Nishimura
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

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import javax.json.JsonObject;
import javax.ws.rs.core.Link;
import org.vx68k.bitbucket.api.BitbucketAccount;
import org.vx68k.bitbucket.api.BitbucketRepository;

/**
 * Client implementation of {@link BitbucketAccount}.
 * This class represents an account by a JSON object.
 *
 * @author Kaz Nishimura
 * @see BitbucketClientUser
 * @since 5.0
 */
public class BitbucketClientAccount extends BitbucketClientObject implements
    BitbucketAccount
{
    /**
     * Name of the {@code created_on} value in a JSON account object.
     */
    private static final String CREATED_ON = "created_on";

    /**
     * Name of the {@code display_name} value in a JSON account object.
     */
    private static final String DISPLAY_NAME = "display_name";

    /**
     * Name of the {@code location} value in a JSON account object.
     */
    private static final String LOCATION = "location";

    /**
     * Name of the {@code username} value in a JSON account object.
     */
    private static final String USERNAME = "username";

    /**
     * Name of the {@code uuid} value in a JSON account object.
     */
    private static final String UUID = "uuid";

    /**
     * Name of the {@code website} value in a JSON account object.
     */
    private static final String WEBSITE = "website";

    /**
     * Name of the {@code repositories} link in a JSON {@link links} object.
     */
    private static final String REPOSITORIES = "repositories";

    /**
     * Constructs this object with no Bitbucket client.
     *
     * @param object JSON object
     * @exception IllegalArgumentException if {@code object} is {@code null}
     */
    public BitbucketClientAccount(final JsonObject object)
    {
        this(object, null);
    }

    /**
     * Constructs this object.
     *
     * @param object JSON object
     * @param client Bitbucket client, or {@code null}
     * @exception IllegalArgumentException if {@code object} is {@code null}
     */
    public BitbucketClientAccount(
        final JsonObject object, final BitbucketClient client)
    {
        super(object, client);

        String type = getType();
        if (!(USER.equals(type) || TEAM.equals(type))) {
            throw new IllegalArgumentException(
                "JSON object type is not user or team");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName()
    {
        JsonObject object = getJsonObject();
        return object.getString(USERNAME, null);
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
    public final String getDisplayName()
    {
        JsonObject object = getJsonObject();
        return object.getString(DISPLAY_NAME, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getWebsite()
    {
        JsonObject object = getJsonObject();
        return object.getString(WEBSITE, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getLocation()
    {
        JsonObject object = getJsonObject();
        return object.getString(LOCATION, null);
    }

    /**
     * {@inheritDoc}
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
     */
    @Override
    public final BitbucketRepository getRepository(final String name)
    {
        BitbucketClient client = getBitbucketClient();
        return client.getRepository(getName(), name);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation returns a {@link PaginatedList} object.</p>
     *
     * @see BitbucketClientRepository#creator(BitbucketClient)
     */
    @Override
    public final Collection<BitbucketRepository> repositories()
    {
        Link repositories = getLink(REPOSITORIES);

        Collection<BitbucketRepository> value = null;
        if (repositories != null) {
            BitbucketClient client = getBitbucketClient();
            value = new PaginatedList<>(
                repositories.getUri(), client,
                BitbucketClientRepository.creator(client));
        }
        return value;
    }
}
