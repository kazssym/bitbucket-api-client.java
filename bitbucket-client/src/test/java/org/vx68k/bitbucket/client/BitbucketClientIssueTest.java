/*
 * BitbucketClientIssueTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.Instant;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.junit.Ignore;
import org.junit.Test;
import org.vx68k.bitbucket.BitbucketIssue;

/**
 * Unit tests for {@link BitbucketClientIssue}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientIssueTest
{
    /**
     * Time when the sample issue 1 was created.
     */
    private static final Instant SAMPLE_ISSUE1_CREATED =
        Instant.parse("2015-01-25T00:50:30.346399Z");

    /**
     * Time when the sample issue 1 was last updated.
     */
    private static final Instant SAMPLE_ISSUE1_UPDATED =
        Instant.parse("2015-08-27T01:00:22.817449Z");

    /**
     * Time when the sample issue 1 was last edited.
     */
    private static final Instant SAMPLE_ISSUE1_EDITED = null;

    /**
     * Blank JSON object only with a type for issues.
     */
    private final JsonObject blankIssue;

    /**
     * Sample JSON object for an issue.
     */
    private final JsonObject sampleIssue1;

    /**
     * Initializes the object.
     */
    public BitbucketClientIssueTest()
    {
        this.blankIssue = Json.createObjectBuilder()
            .add("type", "issue").build();
        try (JsonReader reader = Json.createReader(
            getClass().getResourceAsStream("samples/issue1.json"))) {
            this.sampleIssue1 = reader.readObject();
        }
    }

    /**
     * Tests the constructors.
     */
    @Test
    public void testConstructors()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        BitbucketClientIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getBitbucketClient());

        try {
            new BitbucketClientIssue(null);
            fail();
        }
        catch (final IllegalArgumentException exception) {
        }
    }

    /**
     * Tests {@link BitbucketClientIssue#getRepository()}.
     */
    @Ignore
    @Test
    public void testGetRepository()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getRepository());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertNotNull(issue1.getRepository());
        assertEquals(
            "bitbucket-api-client.java", issue1.getRepository().getName());
    }

    /**
     * Tests {@link BitbucketClientIssue#getId()}.
     */
    @Test
    public void testGetId()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertEquals(0, issue0.getId());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals(1, issue1.getId());
    }

    /**
     * Tests {@link BitbucketClientIssue#getReporter()}.
     */
    @Test
    public void testGetReporter()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getReporter());

        // BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        // assertNotNull(issue1.getReporter());
        // assertNull(issue1.getReporter().getName());
    }

    /**
     * Tests {@link BitbucketClientIssue#getState()}.
     */
    @Test
    public void testGetState()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getState());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals("resolved", issue1.getState());
    }

    /**
     * Tests {@link BitbucketClientIssue#getTitle()}.
     */
    @Test
    public void testGetTitle()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getTitle());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals("Need a README file", issue1.getTitle());
    }

    /**
     * Tests {@link BitbucketClientIssue#getContent()}.
     */
    @Test
    public void testGetContent()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getContent());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertNotNull(issue1.getContent());
        assertEquals("markdown", issue1.getContent().getMarkup());
        assertEquals("", issue1.getContent().getRaw());
    }

    /**
     * Tests {@link BitbucketClientIssue#getKind()}.
     */
    @Test
    public void testGetKind()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getKind());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals("task", issue1.getKind());
    }

    /**
     * Tests {@link BitbucketClientIssue#getPriority()}.
     */
    @Test
    public void testGetPriority()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getPriority());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals("minor", issue1.getPriority());
    }

    /**
     * Tests {@link BitbucketClientIssue#getAssignee()}.
     */
    @Test
    public void testGetAssignee()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getAssignee());

        // BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        // assertNotNull(issue1.getAssignee());
        // assertNull(issue1.getAssignee().getName());
    }

    /**
     * Tests {@link BitbucketClientIssue#getVotes()}.
     */
    @Test
    public void testGetVotes()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertEquals(-1, issue0.getVotes());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals(0, issue1.getVotes());
    }

    /**
     * Tests {@link BitbucketClientIssue#getWatches()}.
     */
    @Test
    public void testGetWatches()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertEquals(-1, issue0.getWatches());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals(1, issue1.getWatches());
    }

    /**
     * Tests {@link BitbucketClientIssue#getCreated()}.
     */
    @Test
    public void testGetCreated()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getCreated());

        JsonObject nulledIssue = Json.createObjectBuilder()
            .add("type", "issue").addNull("created_on").build();
        BitbucketIssue issue0a = new BitbucketClientIssue(nulledIssue);
        assertNull(issue0a.getCreated());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals(SAMPLE_ISSUE1_CREATED, issue1.getCreated());
    }

    /**
     * Tests {@link BitbucketClientIssue#getUpdated()}.
     */
    @Test
    public void testGetUpdated()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getUpdated());

        JsonObject nulledIssue = Json.createObjectBuilder()
            .add("type", "issue").addNull("updated_on").build();
        BitbucketIssue issue0a = new BitbucketClientIssue(nulledIssue);
        assertNull(issue0a.getUpdated());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals(SAMPLE_ISSUE1_UPDATED, issue1.getUpdated());
    }

    /**
     * Tests {@link BitbucketClientIssue#getEdited()}.
     */
    @Test
    public void testGetEdited()
    {
        BitbucketIssue issue0 = new BitbucketClientIssue(blankIssue);
        assertNull(issue0.getEdited());

        JsonObject nulledIssue = Json.createObjectBuilder()
            .add("type", "issue").addNull("edited_on").build();
        BitbucketIssue issue0a = new BitbucketClientIssue(nulledIssue);
        assertNull(issue0a.getEdited());

        BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        assertEquals(SAMPLE_ISSUE1_EDITED, issue1.getEdited());
    }
}
