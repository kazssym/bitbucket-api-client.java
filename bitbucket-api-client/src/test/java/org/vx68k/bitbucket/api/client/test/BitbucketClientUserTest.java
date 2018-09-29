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

package org.vx68k.bitbucket.api.client.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.junit.Test;
import org.vx68k.bitbucket.api.BitbucketUser;
import org.vx68k.bitbucket.api.client.BitbucketClientUser;

/**
 * Unit tests for {@link BitbucketClientUser}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientUserTest
{
    /**
     * Tests the constructor with {@code null}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNull()
    {
        new BitbucketClientUser(null);
        fail();
    }

    /**
     * Tests the constructor with an untyped object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUntyped()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        new BitbucketClientUser(builder.build());
        fail();
    }

    /**
     * Tests the constructor with an object of a wrong type.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrongType()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("type", "not_user");
        new BitbucketClientUser(builder.build());
        fail();
    }

    /**
     * Tests the constructor with a blank {@code "user"} object.
     */
    @Test
    public void testBlankUser()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("type", "user");
        BitbucketUser user = new BitbucketClientUser(builder.build());
        assertNull(user.getName());
        assertNull(user.getUUID());
        assertNull(user.getDisplayName());
        assertNull(user.getWebsite());
        assertNull(user.getLocation());
        assertNull(user.getCreated());
    }
}
