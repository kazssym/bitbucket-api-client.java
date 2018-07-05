/*
 * EventRecorder.java - class EventRecorder
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client.example;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonReader;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.vx68k.bitbucket.webhook.BitbucketEvent;

/**
 * Managed bean to record Bitbucket events.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@ApplicationScoped
@Named
public class EventRecorder implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Entity manager.
     */
    private EntityManager entityManager;

    /**
     * Sets the entity manager to a {@link EntityManager} value.
     *
     * @param value {@link EntityManager} value
     */
    @PersistenceContext(unitName = "Bitbucket")
    public void setEntityManager(final EntityManager value)
    {
        entityManager = value;
    }

    /**
     * Returns a {@link Collection} view of all the records.
     *
     * @return {@link Collection} view of all the records
     */
    public Collection<EventRecord> getRecords()
    {
        TypedQuery<EventRecord> query =
            entityManager.createQuery("SELECT record FROM EventRecord record",
                EventRecord.class);
        return query.getResultList();
    }

    /**
     * Records a Bitbucket event.
     *
     * @param event Bitbucket event to record
     */
    @Transactional
    public void record(@Observes final BitbucketEvent event)
    {
        if (entityManager != null) {
            EventRecord record = new EventRecord(event.toString());
            entityManager.persist(record);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int value = getClass().hashCode();
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object)
    {
        if (this != object) {
            if (object == null || object.getClass() != getClass()) { // NOPMD
                return false;
            }
        }
        return true;
    }

    /**
     * Event record.
     */
    @Table(indexes = {
        @Index(columnList = "recorded", name = "EventRecord_recorded"),
    })
    @Entity(name = "EventRecord")
    public static class EventRecord
    {
        /**
         * Record identifier.
         */
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        private int id;

        /**
         * Recorded timestamp.
         */
        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date recorded;

        /**
         * Event data.
         */
        @Column(columnDefinition = "CLOB")
        @Lob
        private String event;

        /**
         * Constructs this object with no parameters.
         */
        public EventRecord()
        {
            this(null);
        }

        /**
         * Constructs this object with a {@link String} value for {@code event}.
         *
         * @param eventValue {@link String} value for {@code event}
         */
        public EventRecord(final String eventValue)
        {
            id = 0;
            recorded = new Date();
            event = eventValue;
        }

        /**
         * Returns the identifier of this record.
         *
         * @return the identifier of this record
         */
        public final int getId()
        {
            return id;
        }

        /**
         * Sets the identifier of this record to an {@code int} value.
         *
         * @param value {@code int} value
         */
        public final void setId(final int value)
        {
            id = value;
        }

        /**
         * Returns the recorded timestamp.
         *
         * @return the recorded timestamp
         */
        public final Date getRecorded()
        {
            return recorded;
        }

        /**
         * Sets the recorded timestamp to a {@link Date} value.
         *
         * @param value {@link Date} value
         */
        public final void setRecorded(final Date value)
        {
            recorded = value;
        }

        /**
         * Returns the Bitbucket event.
         *
         * @return the Bitbucket event
         */
        public final BitbucketEvent getEvent()
        {
            try (JsonReader reader =
                Json.createReader(new StringReader(event))) {
                return new BitbucketEvent(reader.readObject());
            }
        }

        /**
         * Sets the event to a {@link String} value.
         *
         * @param value {@link String} value
         */
        public final void setEvent(final String value)
        {
            event = value;
        }
    }
}
