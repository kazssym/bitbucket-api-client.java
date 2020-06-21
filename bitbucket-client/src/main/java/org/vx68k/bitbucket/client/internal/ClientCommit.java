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
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbDateFormat;
import org.vx68k.bitbucket.BitbucketRepository;

/**
 * Client implementation class of {@link BitbucketRepository.Commit} for the
 * {@code "commit"} objects.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientCommit implements BitbucketRepository.Commit
{
    private String hash;

    private OffsetDateTime date;

    private String message;

    private ClientRepository repository;

    private List<BitbucketRepository.Commit> parents;

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
    public ClientCommit(final BitbucketRepository.Commit other)
    {
        this.hash = other.getHash();
        this.date = other.getDate();
        this.message = other.getMessage();

        this.repository = new ClientRepository(other.getRepository());
        this.parents = new ArrayList<>(other.getParents());
        this.summary = new ClientRendered(other.getSummary());
    }

    public final String getType()
    {
        return "commit";
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

    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss[.SSSSSS]xxxxx")
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
    public final ClientRepository getRepository()
    {
        return repository;
    }

    public final void setRepository(ClientRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public final List<BitbucketRepository.Commit> getParents()
    {
        return parents;
    }

    public void setParents(List<BitbucketRepository.Commit> parents)
    {
        this.parents = new ArrayList<>(parents);
    }

    @Override
    public final ClientRendered getSummary()
    {
        return summary;
    }

    public final void setSummary(final ClientRendered summary)
    {
        this.summary = new ClientRendered(summary);
    }

    public final ClientRendered getRendered()
    {
        return getSummary();
    }
}
