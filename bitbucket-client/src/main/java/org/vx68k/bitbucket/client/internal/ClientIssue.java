/*
 * ClientIssue.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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
import org.vx68k.bitbucket.BitbucketIssue;

/**
 * Client implementation class of {@link BitbucketIssue} for the
 * {@code "issue"} objects.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientIssue implements BitbucketIssue
{
    private int id;

    private String state;

    private String kind;

    private String priority;

    private String title;

    private OffsetDateTime created;

    private OffsetDateTime updated;

    private OffsetDateTime edited;

    private int votes;

    private int watches;

    private ClientRepository repository;

    private ClientUserAccount reporter;

    private ClientUserAccount assignee;

    private ClientRendered content;

    /**
     * Constructs an issue.
     */
    public ClientIssue()
    {
        // Nothing to do.
    }

    /**
     * Constructs an issue copying another.

     * @param other another issue
     */
    public ClientIssue(final ClientIssue other)
    {
        this.id = other.id;
        this.title = other.title;
        this.state = other.state;
        this.kind = other.kind;
        this.priority = other.priority;
        this.created = other.created;
        this.votes = other.votes;
        this.watches = other.watches;

        this.repository = new ClientRepository(other.repository);
        this.reporter = new ClientUserAccount(other.reporter);
        this.assignee = new ClientUserAccount(other.assignee);
        this.content = new ClientRendered(other.content);
    }

    public final String getType()
    {
        return "issue";
    }

    @Override
    public final int getId()
    {
        return id;
    }

    @Override
    public final String getTitle()
    {
        return title;
    }

    @Override
    public final String getState()
    {
        return state;
    }

    @Override
    public final String getKind()
    {
        return kind;
    }

    @Override
    public final String getPriority()
    {
        return priority;
    }

    @Override
    public final OffsetDateTime getCreated()
    {
        return created;
    }

    @Override
    public final OffsetDateTime getUpdated()
    {
        return updated;
    }

    @Override
    public final OffsetDateTime getEdited()
    {
        return edited;
    }

    @Override
    public final int getVotes()
    {
        return votes;
    }

    @Override
    public final int getWatches()
    {
        return watches;
    }

    public final ClientRepository getRepository()
    {
        return repository;
    }

    @Override
    public final ClientUserAccount getReporter()
    {
        return reporter;
    }

    @Override
    public final ClientUserAccount getAssignee()
    {
        return assignee;
    }

    @Override
    public final ClientRendered getContent()
    {
        return content;
    }

    @Override
    public final Component getComponent()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Milestone getMilestone()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Version getVersion()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
