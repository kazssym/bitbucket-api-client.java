/*
 * ClientRepositoryTest.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.InputStream;
import java.util.UUID;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ClientRepository}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class ClientRepositoryTest
{
    private static final UUID SAMPLE1_UUID =
        UUID.fromString("da0453e5-4546-445a-adde-95e3a505e875");

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
        String string1 = "{}";
        ClientRepository repository1 = jsonb.fromJson(string1, ClientRepository.class);
        assertNull(repository1.getType());
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
        ClientRepository repository1 = null;
        try {
            repository1 = jsonb.fromJson(string1, ClientRepository.class);
            fail();
        }
        catch (final JsonbException e) {
            System.err.println("Caught as expected: " + e.toString());
        }
        assertNull(repository1);
    }

    /**
     * Tests {@link ClientRepository#getUuid()}.
     */
    @Test
    void testUuid1()
    {
        String string1 = "{\"type\":\"repository\"}";
        ClientRepository repository1 = jsonb.fromJson(string1, ClientRepository.class);
        assertNull(repository1.getUuid());
    }

    /**
     * Tests {@link ClientRepository#getUuid()}.
     */
    @Test
    void testUuid2()
    {
        String string1 = "{\"type\":\"repository\",\"uuid\":\"{01234567-89ab-cdef-0123-456789abcdef}\"}";
        ClientRepository repository1 = jsonb.fromJson(string1, ClientRepository.class);
        assertEquals(UUID.fromString("01234567-89ab-cdef-0123-456789abcdef"), repository1.getUuid());
    }

    /**
     * Tests {@link ClientRepository#getUuid()}.
     */
    @Test
    void testUuid3()
    {
        ClientRepository repository1 = jsonb.fromJson(sample1, ClientRepository.class);
        assertEquals(SAMPLE1_UUID, repository1.getUuid());
    }

    /**
     * Tests {@link ClientRepository#getSCM}.
     */
    @Disabled("Need rewrite")
    @Test
    void testGetSCM()
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
