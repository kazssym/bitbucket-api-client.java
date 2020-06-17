/*
 * ClientAccount.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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
import java.util.Map;
import java.util.UUID;
import javax.json.bind.annotation.JsonbProperty;
import javax.ws.rs.core.Link;
import org.vx68k.bitbucket.BitbucketAccount;

/**
 * Abstract client implementation class of {@link BitbucketAccount}.
 *
 * @author Kaz Nishimura
 * @see ClientUserAccount
 * @since 6.0
 */
abstract class ClientAccount implements BitbucketAccount
{
    @JsonbProperty("uuid")
    private UUID uuid;

    @JsonbProperty("username")
    private String name;

    @JsonbProperty("display_name")
    private String displayName;

    @JsonbProperty("website")
    private String website;

    @JsonbProperty("location")
    private String location;

    @JsonbProperty("created_on")
    private Instant created;

    @JsonbProperty("links")
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

    @Override
    public final String toString()
    {
        if (uuid != null) {
            return "{" + uuid.toString() + "}";
        }

        return "-";
    }
}
