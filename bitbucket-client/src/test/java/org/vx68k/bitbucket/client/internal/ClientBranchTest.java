/*
 * ClientBranchTest.java
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
// import java.io.InputStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientBranch}.
 *
 * @author Kaz Nishimura
 */
final class ClientBranchTest
{
    private Jsonb jsonb;

    // private InputStream sample1;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();

        // sample1 = getClass().getResourceAsStream("samples/branch1.json");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        // sample1.close();
        // sample1 = null;

        jsonb.close();
        jsonb = null;
    }

    /**
     * Tests {@link ClientBranch#getType()}.
     */
    @Test
    void testType1()
    {
        String string1 = "{}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNull(branch1.getType());
    }

    /**
     * Tests {@link ClientBranch#getType()}.
     */
    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertEquals("branch", branch1.getType());
    }

    /**
     * Tests {@link ClientBranch#getType()}.
     */
    @Test
    void testType3()
    {
        String string1 = "{\"type\":\"named_branch\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertEquals("named_branch", branch1.getType());
    }

    /**
     * Tests {@link ClientBranch#getType()}.
     */
    @Test
    void testType4()
    {
        String string1 = "{\"type\":\"bookmark\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertEquals("bookmark", branch1.getType());
    }

    /**
     * Tests {@link ClientBranch#getName()}.
     */
    @Test
    void testName1()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNull(branch1.getName());
    }

    /**
     * Tests {@link ClientBranch#getName()}.
     */
    @Test
    void testName2()
    {
        String string1 = "{\"type\":\"branch\",\"name\":\"master\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertEquals("master", branch1.getName());
    }

    /**
     * Tests {@link ClientBranch#getHeads}.
     */
    @Test
    void testHeads1()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNull(branch1.getHeads());
    }

    /**
     * Tests {@link ClientBranch#getHeads}.
     */
    @Test
    void testHeads2()
    {
        String string1 = "{\"type\":\"branch\",\"heads\":[]}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNotNull(branch1.getHeads());
        assertEquals(0, branch1.getHeads().length);
    }
}
