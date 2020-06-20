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
    void testConstructor()
    {
        String string1 = "{}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertEquals("rendered", rendered1.getType());

        String string2 = "{\"type\":\"none\"}";
        ClientRendered rendered2 = jsonb.fromJson(string2, ClientRendered.class);
        assertEquals("rendered", rendered2.getType());
    }

    @Test
    void testMarkup()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getMarkup());

        String string2 = "{\"type\":\"rendered\",\"markup\":\".markup\"}";
        ClientRendered rendered2 = jsonb.fromJson(string2, ClientRendered.class);
        assertEquals(".markup", rendered2.getMarkup());
    }

    @Test
    void testRaw()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getRaw());

        String string2 = "{\"type\":\"rendered\",\"raw\":\".raw\"}";
        ClientRendered rendered2 = jsonb.fromJson(string2, ClientRendered.class);
        assertEquals(".raw", rendered2.getRaw());
    }

    @Test
    void testHtml()
    {
        String string1 = "{\"type\":\"rendered\"}";
        ClientRendered rendered1 = jsonb.fromJson(string1, ClientRendered.class);
        assertNull(rendered1.getHtml());

        String string2 = "{\"type\":\"rendered\",\"html\":\".html\"}";
        ClientRendered rendered2 = jsonb.fromJson(string2, ClientRendered.class);
        assertEquals(".html", rendered2.getHtml());
    }
}
