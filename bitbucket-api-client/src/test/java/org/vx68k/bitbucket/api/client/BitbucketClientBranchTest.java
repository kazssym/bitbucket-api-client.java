/*
 * BitbucketClientBranchTest.java - class BitbucketClientBranchTest
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

package org.vx68k.bitbucket.api.client;

import static org.junit.Assert.assertEquals;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.junit.Test;

/**
 * Unit tests for {@link BitbucketClientBranch}.
 *
 * @author Kaz Nishimura
 */
public class BitbucketClientBranchTest
{
    /**
     * Tests with {@code null}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNull()
    {
        new BitbucketClientBranch(null);
    }

    /**
     * Tests with a {@code "branch"} object.
     */
    @Test
    public void testBranch()
    {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("type", "branch").add("name", "master");
        BitbucketClientBranch branch =
            new BitbucketClientBranch(objectBuilder.build());
        assertEquals("branch", branch.getType());
        assertEquals("master", branch.getName());
    }

    /**
     * Tests with a {@code "named_branch"} object.
     */
    @Test
    public void testNamedBranch()
    {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("type", "named_branch").add("name", "default");
        BitbucketClientBranch branch =
            new BitbucketClientBranch(objectBuilder.build());
        assertEquals("named_branch", branch.getType());
        assertEquals("default", branch.getName());
    }

    /**
     * Tests with a {@code "bookmark"} object.
     */
    @Test
    public void testBookmark()
    {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("type", "bookmark").add("name", "staging");
        BitbucketClientBranch branch =
            new BitbucketClientBranch(objectBuilder.build());
        assertEquals("bookmark", branch.getType());
        assertEquals("staging", branch.getName());
    }
}
