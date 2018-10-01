/*
 * BitbucketClientIssueTest.java
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

package org.vx68k.bitbucket.api.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.BitbucketClientIssue;

/**
 * Unit tests for {@link BitbucketClientIssue}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientIssueTest
{
    /**
     * Adds the {@code issue} type to a JSON object builder.
     *
     * @param builder a JSON object builder
     * @return the same JSON object builder
     */
    static JsonObjectBuilder addIssueType(final JsonObjectBuilder builder)
    {
        return builder.add("type", "issue");
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor()
    {
        try {
            new BitbucketClientIssue(null);
            fail();
        }
        catch (final IllegalArgumentException exception) {
            System.out.format("Caught %s\n", exception);
        }
    }

    /**
     * Tests {@link BitbucketClientIssue#getRepository()}.
     */
    @Ignore
    @Test
    public void testGetRepository()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        addIssueType(builder);

        BitbucketClientIssue issue1 =
            new BitbucketClientIssue(builder.build());
        assertEquals(null, issue1.getRepository());
    }
}
