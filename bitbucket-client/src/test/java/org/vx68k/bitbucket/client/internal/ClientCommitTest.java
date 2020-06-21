/*
 * BitbucketClientCommitTest.java - class BitbucketClientCommitTest
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

package org.vx68k.bitbucket.client.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.time.OffsetDateTime;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientCommit}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class ClientCommitTest
{
    private static final String SAMPLE1_HASH =
        "6799fb47ceaa832d85dadb23f7ec87d62603ce27";

    private static final OffsetDateTime SAMPLE1_DATE =
        OffsetDateTime.parse("2015-01-25T00:31:07+00:00");

    private Jsonb jsonb;

    private InputStream sample1;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/commit1.json");
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
     * Tests {@link ClientCommit#getType()}.
     */
    @Test
    void testType1()
    {
        String string1 = "{}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getType());
    }

    /**
     * Tests {@link ClientCommit#getType()}.
     */
    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertEquals("commit", commit1.getType());
    }

    /**
     * Tests {@link ClientCommit#getType()}.
     */
    @Test
    void testType3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals("commit", commit1.getType());
    }

    /**
     * Tests {@link ClientCommit#getType()}.
     */
    @Test
    void testType4()
    {
        String string1 = "{\"type\":\"other\"}";
        ClientCommit commit1 = null;
        try {
            commit1 = jsonb.fromJson(string1, ClientCommit.class);
            fail();
        }
        catch (final RuntimeException e) {
            System.err.println("Caught as expected: " + e.getMessage());
        }
        assertNull(commit1);
    }

    /**
     * Tests {@link ClientCommit#getHash()}.
     */
    @Test
    void testHash1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getHash());
    }

    /**
     * Tests {@link ClientCommit#getHash()}.
     */
    @Test
    void testHash2()
    {
        String string1 = "{\"type\":\"commit\",\"hash\":\".hash\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertEquals(".hash", commit1.getHash());
    }

    /**
     * Tests {@link ClientCommit#getHash()}.
     */
    @Test
    void testHash3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals(SAMPLE1_HASH, commit1.getHash());
    }

    @Test
    void testDate1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getDate());
    }

    @Test
    void testDate2()
    {
        String string1 = "{\"type\":\"commit\",\"date\":\"2001-01-01T01:23:45+09:00\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45+09:00"), commit1.getDate());
    }

    @Test
    void testDate3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals(SAMPLE1_DATE, commit1.getDate());
    }

    @Test
    void testMessage1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getMessage());
    }

    @Test
    void testMessage2()
    {
        String string1 = "{\"type\":\"commit\",\"message\":\".message\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertEquals(".message", commit1.getMessage());
    }

    @Test
    void testMessage3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals("Created a project.\n", commit1.getMessage());
    }

    @Test
    void testRepository1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getRepository());
    }

    @Test
    void testRepository2()
    {
        String string1 = "{\"type\":\"commit\",\"repository\":{}}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNotNull(commit1.getRepository());
    }

    @Test
    void testRepository3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit1.getRepository());
    }

    @Test
    void testParents1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getParents());
    }

    @Test
    void testParents2()
    {
        String string1 = "{\"type\":\"commit\",\"parents\":[]}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNotNull(commit1.getParents());
        assertEquals(0, commit1.getParents().size());
    }

    @Test
    void testParents3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit1.getParents());
        assertEquals(0, commit1.getParents().size());
    }

    @Disabled("Not implemented yet")
    @Test
    void testAuthor1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNotNull(commit1);
    }

    @Disabled("Not implemented yet")
    @Test
    void testAuthor2()
    {
        String string1 = "{\"type\":\"commit\",\"author\":{}}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNotNull(commit1);
    }

    @Disabled("Not implemented yet")
    @Test
    void testAuthor3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit1);
    }

    @Test
    void testSummary1()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getSummary());
    }

    @Test
    void testSummary2()
    {
        String string1 = "{\"type\":\"commit\",\"summary\":{}}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNotNull(commit1.getSummary());
    }

    @Test
    void testSummary3()
    {
        ClientCommit commit1 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit1.getSummary());
    }
}
