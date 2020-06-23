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

package org.vx68k.bitbucket.client.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.InputStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientRepository}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class ClientRepositoryTest
{
    private Jsonb jsonb;

    private InputStream sample1;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();

        sample1 = getClass().getResourceAsStream("samples/repository1.json");
    }

    @AfterEach
    void tearDown() throws Exception
    {
        sample1.close();
        sample1 = null;

        jsonb.close();
        jsonb = null;
    }

    /**
     * Tests {@link ClientRepository#getType()}.
     */
    @Test
    void testType1()
    {
        // The type is always "repository".

        String string1 = "{}";
        ClientRepository repository1 = jsonb.fromJson(string1, ClientRepository.class);
        assertEquals("repository", repository1.getType());
    }

    /**
     * Tests {@link ClientRepository#getType()}.
     */
    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"repository\"}";
        ClientRepository repository1 = jsonb.fromJson(string1, ClientRepository.class);
        assertEquals("repository", repository1.getType());
    }

    /**
     * Tests {@link ClientRepository#getType()}.
     */
    @Test
    void testType3()
    {
        String string1 = "{\"type\":\"other\"}";
        ClientRepository repository1 = jsonb.fromJson(string1, ClientRepository.class);
        assertEquals("repository", repository1.getType());
    }

    /**
     * Tests {@link ClientRepository#getSCM}.
     */
    @Test
    public void testGetSCM()
    {
        // JsonObject repositoryObject1 = Json.createObjectBuilder()
        //     .add("type", "repository").add("scm", "git").build();
        // ClientRepository repository1 =
        //     new ClientRepository(repositoryObject1);
        // assertEquals("git", repository1.getSCM());

        // JsonObject repositoryObject2 = Json.createObjectBuilder()
        //     .add("type", "repository").add("scm", "hg").build();
        // ClientRepository repository2 =
        //     new ClientRepository(repositoryObject2);
        // assertEquals("hg", repository2.getSCM());
    }
}
