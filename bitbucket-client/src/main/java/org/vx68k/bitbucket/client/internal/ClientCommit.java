/*
 * ClientCommit.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

import java.time.OffsetDateTime;
import java.util.Arrays;
import javax.json.bind.annotation.JsonbDateFormat;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.BitbucketRendered;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Implementation class of {@link BitbucketCommit} for the Bitbucket
 * Cloud REST API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientCommit implements BitbucketCommit
{
    public static final String COMMIT = "commit";

    private String type;

    private String hash;

    private OffsetDateTime date;

    private String message;

    private ClientRepository repository;

    private ClientCommit[] parents;

    private ClientRendered summary;

    /**
     * Constructs a commit.
     */
    public ClientCommit()
    {
        // Nothing to do.
    }

    /**
     * Construct a commit copying another.
     *
     * @param other another commit
     */
    public ClientCommit(final ClientCommit other)
    {
        this.type = other.type;
        this.hash = other.hash;
        this.date = other.date;
        this.message = other.message;

        setRepository(other.repository);
        setParents(other.parents);
        setSummary(other.summary);
    }

    public final String getType()
    {
        return type;
    }

    public final void setType(final String type)
    {
        if (type != null && !(type.equals(COMMIT))) {
            throw new IllegalArgumentException("Type is not of commit objects");
        }
        this.type = type;
    }

    @Override
    public final String getHash()
    {
        return hash;
    }

    public final void setHash(final String hash)
    {
        this.hash = hash;
    }

    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss[.SSSSSS]xxxxx")
    @Override
    public final OffsetDateTime getDate() {
        return date;
    }

    public final void setDate(final OffsetDateTime date) {
        this.date = date;
    }

    @Override
    public final String getMessage()
    {
        return message;
    }

    public final void setMessage(final String message)
    {
        this.message = message;
    }

    @Override
    public final BitbucketRepository getRepository()
    {
        return repository;
    }

    public final void setRepository(ClientRepository repository)
    {
        if (repository != null) {
            repository = new ClientRepository(repository);
        }
        this.repository = repository;
    }

    @Override
    public final BitbucketCommit[] getParents()
    {
        if (parents != null) {
            return Arrays.copyOf(parents, parents.length, BitbucketCommit[].class);
        }
        return null;
    }

    public void setParents(ClientCommit[] parents)
    {
        if (parents != null) {
            parents = Arrays.stream(parents).map(ClientCommit::new)
                .toArray(ClientCommit[]::new);
        }
        this.parents = parents;
    }

    @Override
    public final BitbucketRendered getSummary()
    {
        return summary;
    }

    public final void setSummary(ClientRendered summary)
    {
        if (summary != null) {
            summary = new ClientRendered(summary);
        }
        this.summary = summary;
    }

    public final BitbucketRendered getRendered()
    {
        return getSummary();
    }
}
