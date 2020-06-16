/*
 * ClientRepositoryTest.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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
import static org.junit.Assert.fail;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.Test;

/**
 * Unit tests for {@link ClientRepository}.
 *
 * @author Kaz Nishimura
 * @since 6.0.0
 */
public final class ClientRepositoryTest
{
    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor()
    {
        JsonObject repositoryObject = Json.createObjectBuilder()
            .add("type", "repository").build();
        new ClientRepository(repositoryObject);

        try {
            new ClientRepository(null);
            fail();
        }
        catch (final IllegalArgumentException exception) {
            // OK.
        }
    }

    /**
     * Tests {@link ClientRepository#getSCM}.
     */
    @Test
    public void testGetSCM()
    {
        JsonObject repositoryObject1 = Json.createObjectBuilder()
            .add("type", "repository").add("scm", "git").build();
        ClientRepository repository1 =
            new ClientRepository(repositoryObject1);
        assertEquals("git", repository1.getSCM());

        JsonObject repositoryObject2 = Json.createObjectBuilder()
            .add("type", "repository").add("scm", "hg").build();
        ClientRepository repository2 =
            new ClientRepository(repositoryObject2);
        assertEquals("hg", repository2.getSCM());
    }
}
