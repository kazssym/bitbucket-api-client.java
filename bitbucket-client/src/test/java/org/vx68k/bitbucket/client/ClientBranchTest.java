/*
 * ClientBranchTest.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.Test;

/**
 * Unit tests for {@link BitbucketClientBranch}.
 *
 * @author Kaz Nishimura
 */
public final class ClientBranchTest
{
    /**
     * Tests with {@code null}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNull()
    {
        ClientBranch branch = new ClientBranch();
    }

    /**
     * Tests with a {@code "branch"} object.
     */
    @Test
    public void testBranch() throws Exception
    {
        String string = "{\"type\":\"branch\",\"name\":\"master\"}";
        try (Jsonb jsonb = JsonbBuilder.create()) {
            ClientBranch branch = jsonb.fromJson(string, ClientBranch.class);
            assertEquals("branch", branch.getType());
            assertEquals("master", branch.getName());
        }
    }

    /**
     * Tests with a {@code "named_branch"} object.
     */
    @Test
    public void testNamedBranch() throws Exception
    {
        String string = "{\"type\":\"named_branch\",\"name\":\"master\"}";
        try (Jsonb jsonb = JsonbBuilder.create()) {
            ClientBranch branch = jsonb.fromJson(string, ClientBranch.class);
            assertEquals("named_branch", branch.getType());
            assertEquals("master", branch.getName());
        }
    }

    /**
     * Tests with a {@code "bookmark"} object.
     */
    @Test
    public void testBookmark() throws Exception
    {
        String string = "{\"type\":\"bookmark\",\"name\":\"master\"}";
        try (Jsonb jsonb = JsonbBuilder.create()) {
            ClientBranch branch = jsonb.fromJson(string, ClientBranch.class);
            assertEquals("bookmark", branch.getType());
            assertEquals("master", branch.getName());
        }
    }
}
