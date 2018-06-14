/*
 * BitbucketClientUserTest.java - class BitbucketClientUserTest
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

package org.vx68k.bitbucket.api.client.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.junit.Test;
import org.vx68k.bitbucket.api.client.BitbucketClientUser;

/**
 * Unit tests for {@link BitbucketClientUser}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientUserTest
{
    /**
     * Tests a non-user object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNonUser()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("type", "not_user");
        new BitbucketClientUser(builder.build());
    }

    /**
     * Tests a blank user object.
     */
    @Test
    public void testBlankUser()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("type", "user");
        BitbucketClientUser target = new BitbucketClientUser(builder.build());
        assertEquals("user", target.getType());
        assertNull(target.getName());
        assertNull(target.getDisplayName());
    }
}
