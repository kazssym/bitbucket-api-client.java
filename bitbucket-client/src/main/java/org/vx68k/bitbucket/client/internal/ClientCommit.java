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
import javax.json.bind.annotation.JsonbTypeAdapter;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.BitbucketRendered;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.client.adapter.BitbucketRenderedAdapter;
import org.vx68k.bitbucket.client.adapter.BitbucketRepositoryAdapter;

/**
 * Client implementation class of {@link BitbucketCommit} for the
 * {@code "commit"} objects.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientCommit implements BitbucketCommit
{
    private String type;

    private String hash;

    private OffsetDateTime date;

    private String message;

    private BitbucketRepository repository;

    private BitbucketCommit[] parents;

    private BitbucketRendered summary;

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
    public ClientCommit(final BitbucketCommit other)
    {
        this.type = "commit"; // TODO: Right?
        this.hash = other.getHash();
        this.date = other.getDate();
        this.message = other.getMessage();

        BitbucketRepository otheRepository = other.getRepository();
        if (otheRepository != null) {
            otheRepository = new ClientRepository(otheRepository);
        }
        this.repository = otheRepository;

        BitbucketCommit[] otherParents = other.getParents();
        if (otherParents != null) {
            otherParents = Arrays.stream(otherParents)
                .map(ClientCommit::new)
                .toArray(BitbucketCommit[]::new);
        }
        this.parents = otherParents;

        BitbucketRendered otherSummary = other.getSummary();
        if (otherSummary != null) {
            otherSummary = new ClientRendered(otherSummary);
        }
        this.summary = otherSummary;
    }

    public final String getType()
    {
        return type;
    }

    public final void setType(final String type)
    {
        if (type != null && !(type.equals("commit"))) {
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

    @JsonbTypeAdapter(BitbucketRepositoryAdapter.class)
    @Override
    public final BitbucketRepository getRepository()
    {
        return repository;
    }

    public final void setRepository(BitbucketRepository repository)
    {
        if (repository != null) {
            repository = new ClientRepository(repository);
        }
        this.repository = repository;
    }

    @Override
    public final BitbucketCommit[] getParents()
    {
        return parents;
    }

    public void setParents(BitbucketCommit[] parents)
    {
        if (parents != null) {
            parents = Arrays.stream(parents)
                .map(ClientCommit::new)
                .toArray(BitbucketCommit[]::new);
        }
        this.parents = parents;
    }

    @JsonbTypeAdapter(BitbucketRenderedAdapter.class)
    @Override
    public final BitbucketRendered getSummary()
    {
        return summary;
    }

    public final void setSummary(final BitbucketRendered summary)
    {
        this.summary = new ClientRendered(summary);
    }

    public final BitbucketRendered getRendered()
    {
        return getSummary();
    }
}
