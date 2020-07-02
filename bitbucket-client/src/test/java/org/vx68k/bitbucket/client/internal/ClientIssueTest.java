/*
 * BitbucketClientIssueTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
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
    private static final OffsetDateTime SAMPLE1_CREATED =
        OffsetDateTime.parse("2015-01-25T00:50:30.346399Z");

    /**
     * Time when the sample issue 1 was last updated.
     */
    private static final OffsetDateTime SAMPLE1_UPDATED =
        OffsetDateTime.parse("2015-08-27T01:00:22.817449Z");

    /**
     * Time when the sample issue 1 was last edited.
     */
    private static final OffsetDateTime SAMPLE1_EDITED = null;

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
     * Tests {@link ClientIssue#getType()}.
     */
    @Test
    void testType1()
    {
        String string1 = "{}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getType());
    }

    /**
     * Tests {@link ClientIssue#getType()}.
     */
    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals("issue", issue1.getType());
    }

    /**
     * Tests {@link ClientIssue#getType()}.
     */
    @Test
    void testType3()
    {
        String string1 = "{\"type\":\"other\"}";
        ClientIssue issue1 = null;
        try {
            issue1 = jsonb.fromJson(string1, ClientIssue.class);
            fail();
        }
        catch (final JsonbException e) {
            Logger.getGlobal().info("Caught an exception as expected");
        }
        assertNull(issue1);
    }

    /**
     * Tests {@link ClientIssue#getId()}.
     */
    @Test
    void testId1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(0, issue1.getId());
    }

    /**
     * Tests {@link ClientIssue#getId()}.
     */
    @Test
    void testId2()
    {
        String string1 = "{\"type\":\"issue\",\"id\":1}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(1, issue1.getId());
    }

    /**
     * Tests {@link ClientIssue#getId()}.
     */
    @Test
    void testId3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(1, issue1.getId());
    }

    /**
     * Tests {@link ClientIssue#getTitle()}.
     */
    @Test
    void testTitle1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getTitle());
    }

    /**
     * Tests {@link ClientIssue#getTitle()}.
     */
    @Test
    void testTitle2()
    {
        String string1 = "{\"type\":\"issue\",\"title\":\".title\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(".title", issue1.getTitle());
    }

    /**
     * Tests {@link ClientIssue#getTitle()}.
     */
    @Test
    void testTitle3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("Need a README file", issue1.getTitle());
    }

    /**
     * Tests {@link ClientIssue#getKind()}.
     */
    @Test
    void testKind1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getKind());
    }

    /**
     * Tests {@link ClientIssue#getKind()}.
     */
    @Test
    void testKind2()
    {
        String string1 = "{\"type\":\"issue\",\"kind\":\".kind\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(".kind", issue1.getKind());
    }

    /**
     * Tests {@link ClientIssue#getKind()}.
     */
    @Test
    void testKind3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("task", issue1.getKind());
    }

    /**
     * Tests {@link ClientIssue#getPriority()}.
     */
    @Test
    void testPriority1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getPriority());
    }

    /**
     * Tests {@link ClientIssue#getPriority()}.
     */
    @Test
    void testPriority2()
    {
        String string1 = "{\"type\":\"issue\",\"priority\":\".priority\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(".priority", issue1.getPriority());
    }

    /**
     * Tests {@link ClientIssue#getPriority()}.
     */
    @Test
    void testPriority3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("minor", issue1.getPriority());
    }

    /**
     * Tests {@link ClientIssue#getState()}.
     */
    @Test
    void testState1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getState());
    }

    /**
     * Tests {@link ClientIssue#getState()}.
     */
    @Test
    void testState2()
    {
        String string1 = "{\"type\":\"issue\",\"state\":\".state\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(".state", issue1.getState());
    }

    /**
     * Tests {@link ClientIssue#getState()}.
     */
    @Test
    void testState3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals("resolved", issue1.getState());
    }

    /**
     * Tests {@link ClientIssue#getCreated()}.
     */
    @Test
    void testCreated1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getCreated());
    }

    /**
     * Tests {@link ClientIssue#getCreated()}.
     */
    @Test
    void testCreated2()
    {
        String string1 = "{\"type\":\"issue\",\"created_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), issue1.getCreated());
    }

    /**
     * Tests {@link ClientIssue#getCreated()}.
     */
    @Test
    void testCreated3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(SAMPLE1_CREATED, issue1.getCreated());
    }

    /**
     * Tests {@link ClientIssue#getUpdated()}.
     */
    @Test
    void testUpdated1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getUpdated());
    }

    /**
     * Tests {@link ClientIssue#getUpdated()}.
     */
    @Test
    void testUpdated2()
    {
        String string1 = "{\"type\":\"issue\",\"updated_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), issue1.getUpdated());
    }

    /**
     * Tests {@link ClientIssue#getUpdated()}.
     */
    @Test
    void testUpdated3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(SAMPLE1_UPDATED, issue1.getUpdated());
    }

    /**
     * Tests {@link ClientIssue#getEdited()}.
     */
    @Test
    void testEdited1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getEdited());
    }

    /**
     * Tests {@link ClientIssue#getEdited()}.
     */
    @Test
    void testEdited2()
    {
        String string1 = "{\"type\":\"issue\",\"edited_on\":\"2001-01-01T01:23:45.678901+09:00\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45.678901+09:00"), issue1.getEdited());
    }

    /**
     * Tests {@link ClientIssue#getEdited()}.
     */
    @Test
    void testEdited3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(SAMPLE1_EDITED, issue1.getEdited());
    }

    /**
     * Tests {@link ClientIssue#getVotes()}.
     */
    @Test
    void testVotes1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(0, issue1.getVotes());
    }

    /**
     * Tests {@link ClientIssue#getVotes()}.
     */
    @Test
    void testVotes2()
    {
        String string1 = "{\"type\":\"issue\",\"votes\":1}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(1, issue1.getVotes());
    }

    /**
     * Tests {@link ClientIssue#getVotes()}.
     */
    @Test
    void testVotes3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(0, issue1.getVotes());
    }

    /**
     * Tests {@link ClientIssue#getWatches()}.
     */
    @Test
    void testWatches1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(0, issue1.getWatches());
    }

    /**
     * Tests {@link ClientIssue#getWatches()}.
     */
    @Test
    void testWatches2()
    {
        String string1 = "{\"type\":\"issue\",\"watches\":1}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertEquals(1, issue1.getWatches());
    }

    /**
     * Tests {@link ClientIssue#getWatches()}.
     */
    @Test
    void testWatches3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertEquals(1, issue1.getWatches());
    }

    /**
     * Tests {@link ClientIssue#getRepository()}.
     */
    @Test
    void testRepository1()
    {
        String string1 = "{\"type\":\"issue\"}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNull(issue1.getRepository());
    }

    /**
     * Tests {@link ClientIssue#getRepository()}.
     */
    @Test
    void testRepository2()
    {
        String string1 = "{\"type\":\"issue\",\"repository\":{}}";
        ClientIssue issue1 = jsonb.fromJson(string1, ClientIssue.class);
        assertNotNull(issue1.getRepository());
    }

    /**
     * Tests {@link ClientIssue#getRepository()}.
     */
    @Test
    void testRepository3()
    {
        ClientIssue issue1 = jsonb.fromJson(sample1, ClientIssue.class);
        assertNotNull(issue1.getRepository());
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
