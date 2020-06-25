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

package org.vx68k.bitbucket.client;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import org.vx68k.bitbucket.BitbucketIssue;
import org.vx68k.bitbucket.client.internal.ClientIssue;

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
    private static final String ENDPOINT_URI =
        "https://api.bitbucket.org/2.0/"
            + "repositories/vx68k/bitbucket-api-client.java/issues";

    /**
     * Tests {@link PaginatedList#get(int)}.
     */
    @Test
    public void testGet1()
    {
        BitbucketClient client = BitbucketClient.getDefaultInstance();
        List<ClientIssue> issues =
            new PaginatedList<>(client, ENDPOINT_URI, ClientIssue.class);

        BitbucketIssue issue = issues.get(0);
        assertTrue(issue instanceof ClientIssue);
    }

    /**
     * Tests {@link PaginatedList#size()}.
     */
    @Test
    public void testSize1()
    {
        BitbucketClient client = BitbucketClient.getDefaultInstance();
        List<ClientIssue> issues =
            new PaginatedList<>(client, ENDPOINT_URI, ClientIssue.class);

        assertTrue(issues.size() > 0);
    }
}
