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
     * Tests {@link ClientCommit#ClientCommit}.
     */
    @Test
    void testConstructor()
    {
        // The type is always "commit".

        String string1 = "{}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertEquals("commit", commit1.getType());

        String string2 = "{\"type\":\"none\"}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertEquals("commit", commit2.getType());
    }

    @Test
    void testHash()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getHash());

        String string2 = "{\"type\":\"commit\",\"hash\":\".hash\"}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertEquals(".hash", commit2.getHash());

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals(SAMPLE1_HASH, commit3.getHash());
    }

    @Test
    void testDate()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getDate());

        String string2 = "{\"type\":\"commit\",\"date\":\"2001-01-01T01:23:45+09:00\"}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertEquals(OffsetDateTime.parse("2001-01-01T01:23:45+09:00"), commit2.getDate());

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals(SAMPLE1_DATE, commit3.getDate());
    }

    @Test
    void testMessage()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getMessage());

        String string2 = "{\"type\":\"commit\",\"message\":\".message\"}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertEquals(".message", commit2.getMessage());

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertEquals("Created a project.\n", commit3.getMessage());
    }

    void testRepository()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getRepository());

        String string2 = "{\"type\":\"commit\",\"repository\":{}}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertNotNull(commit2.getRepository());

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit3.getRepository());
    }

    @Test
    void testParents()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getParents());

        String string2 = "{\"type\":\"commit\",\"parents\":[]}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertNotNull(commit2.getParents());
        assertEquals(0, commit2.getParents().size());

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit3.getParents());
        assertEquals(0, commit3.getParents().size());
    }

    @Disabled("Not implemented yet")
    @Test
    void testAuthor()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNotNull(commit1);

        String string2 = "{\"type\":\"commit\",\"author\":{}}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertNotNull(commit2);

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit3);
    }

    @Test
    void testSummary()
    {
        String string1 = "{\"type\":\"commit\"}";
        ClientCommit commit1 = jsonb.fromJson(string1, ClientCommit.class);
        assertNull(commit1.getSummary());

        String string2 = "{\"type\":\"commit\",\"summary\":{}}";
        ClientCommit commit2 = jsonb.fromJson(string2, ClientCommit.class);
        assertNotNull(commit2.getSummary());

        ClientCommit commit3 = jsonb.fromJson(sample1, ClientCommit.class);
        assertNotNull(commit3.getSummary());
    }
}
