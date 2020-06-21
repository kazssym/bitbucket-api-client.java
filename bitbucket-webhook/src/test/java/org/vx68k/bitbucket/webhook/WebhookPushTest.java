/*
 * WebhookPushTest.java
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

package org.vx68k.bitbucket.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link WebhookPush}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
final class WebhookPushTest
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

    /**
     * Tests a {@code null} object.
     */
    @Test
    void testChanges()
    {
        String string1 = "{}";
        WebhookPush push1 = jsonb.fromJson(string1, WebhookPush.class);
        assertNull(push1.getChanges());

        String string2 = "{\"changes\":[]}";
        WebhookPush push2 = jsonb.fromJson(string2, WebhookPush.class);
        assertNotNull(push2.getChanges());
        assertEquals(0, push2.getChanges().size());
    }
}
