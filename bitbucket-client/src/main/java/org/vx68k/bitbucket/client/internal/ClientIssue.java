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
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import org.vx68k.bitbucket.BitbucketIssue;
import org.vx68k.bitbucket.BitbucketRendered;
import org.vx68k.bitbucket.BitbucketRepository;
import org.vx68k.bitbucket.BitbucketUserAccount;

/**
 * Client implementation class of {@link BitbucketIssue} for the
 * {@code "issue"} objects.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientIssue implements BitbucketIssue
{
    private int id = 0;

    private String state;

    private String kind;

    private String priority;

    private String title;

    private OffsetDateTime created;

    private OffsetDateTime updated;

    private OffsetDateTime edited;

    private int votes = 0;

    private int watches = 0;

    private BitbucketRepository repository;

    private BitbucketRendered content;

    private BitbucketUserAccount reporter;

    private BitbucketUserAccount assignee;

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
        this.id = other.getId();
        this.title = other.getTitle();
        this.kind = other.getKind();
        this.priority = other.getPriority();
        this.state = other.getState();
        this.created = other.getCreated();
        this.updated = other.getUpdated();
        this.edited = other.getEdited();
        this.votes = other.getVotes();
        this.watches = other.getWatches();

        this.repository = new ClientRepository(other.repository);
        this.content = new ClientRendered(other.content);
        this.reporter = new ClientUserAccount(other.reporter);
        this.assignee = new ClientUserAccount(other.assignee);
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

    public final void setId(final int id)
    {
        this.id = id;
    }

    @Override
    public final String getTitle()
    {
        return title;
    }

    public final void setTitle(final String title)
    {
        this.title = title;
    }

    @Override
    public final String getKind()
    {
        return kind;
    }

    public final void setKind(final String kind)
    {
        this.kind = kind;
    }

    @Override
    public final String getPriority()
    {
        return priority;
    }

    public final void setPriority(final String priority)
    {
        this.priority = priority;
    }

    @Override
    public final String getState()
    {
        return state;
    }

    public final void setState(final String state)
    {
        this.state = state;
    }

    @JsonbProperty("created_on")
    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss[.SSSSSS]xxxxx")
    @Override
    public final OffsetDateTime getCreated()
    {
        return created;
    }

    @JsonbProperty("created_on")
    public final void setCreated(final OffsetDateTime created)
    {
        this.created = created;
    }

    @JsonbProperty("updated_on")
    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss[.SSSSSS]xxxxx")
    @Override
    public final OffsetDateTime getUpdated()
    {
        return updated;
    }

    @JsonbProperty("updated_on")
    public final void setUpdated(final OffsetDateTime updated)
    {
        this.updated = updated;
    }

    @JsonbProperty("edited_on")
    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss[.SSSSSS]xxxxx")
    @Override
    public final OffsetDateTime getEdited()
    {
        return edited;
    }

    @JsonbProperty("edited_on")
    public final void setEdited(final OffsetDateTime edited)
    {
        this.edited = edited;
    }

    @Override
    public final int getVotes()
    {
        return votes;
    }

    public final void setVotes(final int votes)
    {
        this.votes = votes;
    }

    @Override
    public final int getWatches()
    {
        return watches;
    }

    public final void setWatches(final int watches)
    {
        this.watches = watches;
    }

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
    public final BitbucketRendered getContent()
    {
        return content;
    }

    public final void setContent(ClientRendered content)
    {
        if (content != null) {
            content = new ClientRendered(content);
        }
        this.content = content;
    }

    @Override
    public final BitbucketUserAccount getReporter()
    {
        return reporter;
    }

    public final void setReporter(ClientUserAccount reporter)
    {
        if (reporter != null) {
            reporter = new ClientUserAccount(reporter);
        }
        this.reporter = reporter;
    }

    @Override
    public final BitbucketUserAccount getAssignee()
    {
        return assignee;
    }

    public final void setAssignee(ClientUserAccount assignee)
    {
        if (assignee != null) {
            assignee = new ClientUserAccount(assignee);
        }
        this.assignee = assignee;
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
