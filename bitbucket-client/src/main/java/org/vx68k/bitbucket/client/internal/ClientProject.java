/*
 * ClientProject.java
 * Copyright (C) 2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client.internal;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.json.bind.annotation.JsonbTypeAdapter;
import org.vx68k.bitbucket.BitbucketProject;
import org.vx68k.bitbucket.client.adapter.LinkMapAdapter;
import org.vx68k.bitbucket.client.adapter.UUIDAdapter;

/**
 * Implementation class of {@link BitbucketProject} for the Bitbucket Cloud
 * REST API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientProject implements BitbucketProject
{
    public static final String PROJECT = "project";

    private String type;

    private UUID uuid;

    private String key;

    private String name;

    private Map<String, URI> links;

    public ClientProject()
    {
        // Nothing to do.
    }

    public ClientProject(final ClientProject other)
    {
        this.type = other.type;
        this.uuid = other.uuid;
        this.key = other.key;
        this.name = other.name;

        setLinks(other.links);
    }

    public final String getType()
    {
        return type;
    }

    public final void setType(final String type)
    {
        if (type != null && !(type.equals(PROJECT))) {
            throw new IllegalArgumentException("Type is not of project objects");
        }
        this.type = type;
    }

    @JsonbTypeAdapter(UUIDAdapter.class)
    @Override
    public final UUID getUuid()
    {
        return uuid;
    }

    public final void setUuid(final UUID uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public final String getKey()
    {
        return key;
    }

    public final void setKey(final String key)
    {
        this.key = key;
    }

    @Override
    public final String getName()
    {
        return name;
    }

    public final void setName(final String name)
    {
        this.name = name;
    }

    @JsonbTypeAdapter(LinkMapAdapter.class)
    @Override
    public final Map<String, URI> getLinks()
    {
        return links;
    }

    public final void setLinks(Map<String, URI> links)
    {
        if (links != null) {
            links = new HashMap<>(links);
        }
        this.links = links;
    }
}
