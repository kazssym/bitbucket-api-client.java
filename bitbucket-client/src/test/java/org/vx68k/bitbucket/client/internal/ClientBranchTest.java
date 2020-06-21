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
     * Tests {@link ClientBranch#ClientBranch}.
     */
    @Test
    void testConstructor()
    {
        String string1 = "{}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNull(branch1.getType());

        String string2 = "{\"type\":\"branch\"}";
        ClientBranch branch2 = jsonb.fromJson(string2, ClientBranch.class);
        assertEquals("branch", branch2.getType());

        String string3 = "{\"type\":\"named_branch\"}";
        ClientBranch branch3 = jsonb.fromJson(string3, ClientBranch.class);
        assertEquals("named_branch", branch3.getType());

        String string4 = "{\"type\":\"bookmark\"}";
        ClientBranch branch4 = jsonb.fromJson(string4, ClientBranch.class);
        assertEquals("bookmark", branch4.getType());
    }

    /**
     * Tests {@link ClientBranch#getName}.
     */
    @Test
    void testName()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNull(branch1.getName());

        String string2 = "{\"type\":\"branch\",\"name\":\"master\"}";
        ClientBranch branch2 = jsonb.fromJson(string2, ClientBranch.class);
        assertEquals("master", branch2.getName());
    }

    /**
     * Tests {@link ClientBranch#getHeads}.
     */
    @Test
    void testHeads()
    {
        String string1 = "{\"type\":\"branch\"}";
        ClientBranch branch1 = jsonb.fromJson(string1, ClientBranch.class);
        assertNull(branch1.getHeads());

        String string2 = "{\"type\":\"branch\",\"heads\":[]}";
        ClientBranch branch2 = jsonb.fromJson(string2, ClientBranch.class);
        assertNotNull(branch2.getHeads());
        assertEquals(0, branch2.getHeads().size());
    }
}
