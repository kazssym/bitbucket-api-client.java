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
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.InputStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientCommit}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public final class ClientCommitTest
{
    private static final String SAMPLE1_HASH =
        "6799fb47ceaa832d85dadb23f7ec87d62603ce27";

    private Jsonb jsonb;

    private InputStream sample1;

    @BeforeEach
    public void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/commit1.json");
    }

    @AfterEach
    public void tearDown() throws Exception
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
    public void testConstructor()
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
    public void testHash()
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
    public void testMessage()
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
}
