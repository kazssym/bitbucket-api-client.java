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

package org.vx68k.bitbucket.client;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbProperty;
import javax.ws.rs.core.Link;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Client implementation of {@link BitbucketAccount}.
 *
 * @author Kaz Nishimura
 * @see ClientUserAccount
 * @since 6.0.0
 */
abstract class ClientAccount implements BitbucketAccount
{
    /**
     * Name of the {@code uuid} value in a JSON account object.
     */
    private static final String JSON_UUID = "uuid";

    /**
     * Name of the {@code username} value in a JSON account object.
     */
    private static final String JSON_NAME = "username";

    /**
     * Name of the {@code display_name} value in a JSON account object.
     */
     private static final String JSON_DISPLAY_NAME = "display_name";

    /**
     * Name of the {@code website} value in a JSON account object.
     */
    private static final String JSON_WEBSITE = "website";

    /**
     * Name of the {@code location} value in a JSON account object.
     */
    private static final String JSON_LOCATION = "location";

    /**
     * Name of the {@code created_on} value in a JSON account object.
     */
    private static final String JSON_CREATED = "created_on";

    private static final String JSON_LINKS = "links";

    private BitbucketClient bitbucketClient;

    @JsonbProperty(JSON_UUID)
    private UUID uuid;

    @JsonbProperty(JSON_NAME)
    private String name;

    @JsonbProperty(JSON_DISPLAY_NAME)
    private String displayName;

    @JsonbProperty(JSON_WEBSITE)
    private String website;

    @JsonbProperty(JSON_LOCATION)
    private String location;

    @JsonbProperty(JSON_CREATED)
    private Instant created;

    @JsonbProperty(JSON_LINKS)
    private Map<String, Link> links;

    /**
     * Constructs an account object.
     */
    protected ClientAccount()
    {
        // Nothing to do.
    }

    /**
     * Constructs an account object copying another.
     *
     * @param other another account object
     */
    protected ClientAccount(final ClientAccount other)
    {
        this.uuid = other.uuid;
        this.name = other.name;
        this.displayName = other.displayName;
        this.website = other.website;
        this.location = other.location;
        this.created = other.created;
    }

    /**
     * Constructs an account object from a JSON object.
     *
     * @param json a JSON object
     * @exception IllegalArgumentException if {@code object} is {@code null}
     */
    protected ClientAccount(final JsonObject json)
    {
        if (json == null) {
            throw new IllegalArgumentException("JsonObject is null");
        }

        this.uuid = JsonUtilities.toUUID(json.get(JSON_UUID));
        this.name = json.getString(JSON_NAME, null);
        this.displayName = json.getString(JSON_DISPLAY_NAME, null);
        this.website = json.getString(JSON_WEBSITE, null);
        this.location = json.getString(JSON_LOCATION, null);
        this.created = JsonUtilities.toInstant(json.get(JSON_CREATED));
    }

    /**
     * Returns the Bitbucket API client associated to the account object.
     *
     * @return the Bitbucket API client associated to the account object
     */
    public final BitbucketClient getBitbucketClient()
    {
        return bitbucketClient;
    }

    /**
     * Sets the Bitbucket API client associated to the account object.
     *
     * @param client a {@link BitbucketClient} object
     */
    public final void setBitbucketClient(final BitbucketClient client)
    {
        this.bitbucketClient = client;
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
     * Sets the UUID of the account.
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
     * Sets the name of the account.
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
    public final String getDisplayName()
    {
        return displayName;
    }

    /**
     * Sets the display name of the account.
     *
     * @param displayName a string object for the name
     */
    public final void setDisplayName(final String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getWebsite()
    {
        return website;
    }

    /**
     * Sets the website of the account.
     *
     * @param website a string object for the website
     */
    public final void setWebsite(final String website)
    {
        this.website = website;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getLocation()
    {
        return location;
    }

    /**
     * Sets the location of the accout.
     *
     * @param location a string object for the location
     */
    public final void setLocation(final String location)
    {
        this.location = location;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Instant getCreated()
    {
        return created;
    }

    /**
     * Sets the creation time of the account.
     *
     * @param created a {@link Instant} object for the create time
     */
    public final void setCreated(final Instant created)
    {
        this.created = created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BitbucketRepository getRepository(final String repositoryName)
    {
        BitbucketClient client = getBitbucketClient();
        return client.getRepository(getName(), repositoryName);
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
        Link repositories = links.get("repositories");

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
