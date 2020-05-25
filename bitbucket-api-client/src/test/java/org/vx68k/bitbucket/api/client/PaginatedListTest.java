/*
 * PaginatedListTest.java
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

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;
import org.junit.Test;
import org.vx68k.bitbucket.api.BitbucketIssue;

/**
 * Unit tests for {@link PaginatedList}.
 *
 * @author Kaz Nishimura
 */
public class PaginatedListTest
{
    /**
     * Test resource.
     */
    private static final URI TEST_URI =
        URI.create("https://api.bitbucket.org/2.0/"
            + "repositories/vx68k/bitbucket-api-client.java/issues");

    /**
     * Tests {@link PaginatedList#get(int)}.
     */
    @Test
    public void testGet()
    {
        BitbucketClient client = BitbucketClient.getDefaultInstance();
        List<BitbucketIssue> issues = new PaginatedList<>(
            TEST_URI, client, BitbucketClientIssue.creator(client));

        BitbucketIssue issue = issues.get(0);
        assertTrue(issue instanceof BitbucketClientIssue);
    }

    /**
     * Tests {@link PaginatedList#size()}.
     */
    @Test
    public void testSize()
    {
        BitbucketClient client = BitbucketClient.getDefaultInstance();
        List<BitbucketIssue> issues = new PaginatedList<>(
            TEST_URI, client, BitbucketClientIssue.creator(client));

        assertTrue(issues.size() >= 0);
    }
}
