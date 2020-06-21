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

package org.vx68k.bitbucket.client.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.InputStream;
import java.time.Instant;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientIssue}.
 *
 * @author Kaz Nishimura
 */
final class ClientIssueTest
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

    private Jsonb jsonb;

    private InputStream sample1;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/issue1.json");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        sample1.close();
        sample1 = null;

        jsonb.close();
        jsonb = null;
    }

    /**
     * Tests {@link ClientIssue#getType}.
     */
    @Test
    void testType()
    {
        String string1 = "{}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals("issue", issue1.getType());

        String string2 = "{\"type\":\"issue\"}";
        ClientIssue issue2 = jsonb.fromJson(string2, ClientIssue.class);
        assertEquals("issue", issue2.getType());

        String string3 = "{\"type\":\"none\"}";
        ClientIssue issue3 = jsonb.fromJson(string3, ClientIssue.class);
        assertEquals("issue", issue3.getType());
    }

    /**
     * Tests {@link ClientIssue#getId}.
     */
    @Test
    void testId()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(0, issue1.getId());

        String string2 = "{\"type\":\"branch\",\"id\":1}";
        ClientIssue issue2 = jsonb.fromJson(string2, ClientIssue.class);
        assertEquals(1, issue2.getId());

        ClientIssue issue3 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(1, issue3.getId());
    }

    /**
     * Tests {@link ClientIssue#getTitle}.
     */
    @Test
    void testTitle()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getTitle());

        String string2 = "{\"type\":\"branch\",\"title\":\".title\"}";
        ClientIssue issue2 = jsonb.fromJson(string2, ClientIssue.class);
        assertEquals(".title", issue2.getTitle());

        ClientIssue issue3 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("Need a README file", issue3.getTitle());
    }

    /**
     * Tests {@link ClientIssue#getKind}.
     */
    @Test
    void testKind()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getKind());

        String string2 = "{\"type\":\"branch\",\"kind\":\".kind\"}";
        ClientIssue issue2 = jsonb.fromJson(string2, ClientIssue.class);
        assertEquals(".kind", issue2.getKind());

        ClientIssue issue3 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("task", issue3.getKind());
    }

    /**
     * Tests {@link ClientIssue#getPriority}.
     */
    @Test
    void testPriority()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getPriority());

        String string2 = "{\"type\":\"branch\",\"priority\":\".priority\"}";
        ClientIssue issue2 = jsonb.fromJson(string2, ClientIssue.class);
        assertEquals(".priority", issue2.getPriority());

        ClientIssue issue3 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("minor", issue3.getPriority());
    }

    /**
     * Tests {@link ClientIssue#getState}.
     */
    @Test
    void testState()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getState());

        String string2 = "{\"type\":\"branch\",\"state\":\".state\"}";
        ClientIssue issue2 = jsonb.fromJson(string2, ClientIssue.class);
        assertEquals(".state", issue2.getState());

        ClientIssue issue3 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("resolved", issue3.getState());
    }

    /**
     * Tests {@link ClientIssue#getCreated()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetCreated()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getCreated());

        // JsonObject nulledIssue = Json.createObjectBuilder()
        //     .add("type", "issue").addNull("created_on").build();
        // BitbucketIssue issue0a = new ClientIssue(nulledIssue);
        // assertNull(issue0a.getCreated());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertEquals(SAMPLE_ISSUE1_CREATED, issue1.getCreated());
    }

    /**
     * Tests {@link ClientIssue#getUpdated()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetUpdated()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getUpdated());

        // JsonObject nulledIssue = Json.createObjectBuilder()
        //     .add("type", "issue").addNull("updated_on").build();
        // BitbucketIssue issue0a = new ClientIssue(nulledIssue);
        // assertNull(issue0a.getUpdated());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertEquals(SAMPLE_ISSUE1_UPDATED, issue1.getUpdated());
    }

    /**
     * Tests {@link ClientIssue#getEdited()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetEdited()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getEdited());

        // JsonObject nulledIssue = Json.createObjectBuilder()
        //     .add("type", "issue").addNull("edited_on").build();
        // BitbucketIssue issue0a = new ClientIssue(nulledIssue);
        // assertNull(issue0a.getEdited());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertEquals(SAMPLE_ISSUE1_EDITED, issue1.getEdited());
    }

    /**
     * Tests {@link ClientIssue#getVotes()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetVotes()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertEquals(-1, issue0.getVotes());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertEquals(0, issue1.getVotes());
    }

    /**
     * Tests {@link ClientIssue#getWatches()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetWatches()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertEquals(-1, issue0.getWatches());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertEquals(1, issue1.getWatches());
    }

    /**
     * Tests {@link ClientIssue#getRepository()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetRepository()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getRepository());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertNotNull(issue1.getRepository());
        // assertEquals(
        //     "bitbucket-api-client.java", issue1.getRepository().getName());
    }

    /**
     * Tests {@link ClientIssue#getReporter()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetReporter()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getReporter());

        // BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        // assertNotNull(issue1.getReporter());
        // assertNull(issue1.getReporter().getName());
    }

    /**
     * Tests {@link ClientIssue#getContent()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetContent()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getContent());

        // BitbucketIssue issue1 = new ClientIssue(sampleIssue1);
        // assertNotNull(issue1.getContent());
        // assertEquals("markdown", issue1.getContent().getMarkup());
        // assertEquals("", issue1.getContent().getRaw());
    }

    /**
     * Tests {@link ClientIssue#getAssignee()}.
     */
    @Disabled("Not implemented yet")
    @Test
    void testGetAssignee()
    {
        // BitbucketIssue issue0 = new ClientIssue(blankIssue);
        // assertNull(issue0.getAssignee());

        // BitbucketIssue issue1 = new BitbucketClientIssue(sampleIssue1);
        // assertNotNull(issue1.getAssignee());
        // assertNull(issue1.getAssignee().getName());
    }
}
