/*
 * ClientTeamAccountTest.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ClientRenderedTest
{
    private Jsonb jsonb;

    @BeforeEach
    void setUp()
    {
        jsonb = JsonbBuilder.create();
    }

    @AfterEach
    void tearDown() throws Exception
    {
        jsonb.close();
        jsonb = null;
    }

    @Test
    void testType1()
    {
        String string1 = "{}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getType());
    }

    @Test
    void testType2()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertEquals("rendered", rendered1.getType());
    }

    @Test
    void testMarkup1()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getMarkup());
    }

    @Test
    void testMarkup2()
    {
        String string1 = "{\"type\":\"rendered\",\"markup\":\".markup\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertEquals(".markup", rendered1.getMarkup());
    }

    @Test
    void testRaw1()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getRaw());
    }

    @Test
    void testRaw2()
    {
        String string1 = "{\"type\":\"rendered\",\"raw\":\".raw\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertEquals(".raw", rendered1.getRaw());
    }

    @Test
    void testHtml1()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getHtml());
    }

    @Test
    void testHtml2()
    {
        String string1 = "{\"type\":\"rendered\",\"html\":\".html\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertEquals(".html", rendered1.getHtml());
    }
}
