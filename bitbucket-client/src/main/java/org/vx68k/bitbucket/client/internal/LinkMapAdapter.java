/*
 * LinkMapAdapter.java
 * Copyright (C) 2020 Kaz Nishimura
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

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

public class LinkMapAdapter implements JsonbAdapter<Map<String, URI>, JsonObject>
{
    @Override
    public final JsonObject adaptToJson(final Map<String, URI> links)
    {
        Map<String, Object> map = links.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry<String, URI>::getKey,
                (entry) -> {
                    URI uri = entry.getValue();
                    return Collections.singletonMap("href", uri.toString());
                }));
        return Json.createObjectBuilder(map).build();
    }

    @Override
    public final Map<String, URI> adaptFromJson(final JsonObject json)
    {
        return json.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry<String, JsonValue>::getKey,
                (entry) -> {
                    JsonObject link = (JsonObject) entry.getValue();
                    String string = link.getString("href", null);
                    if (string != null) {
                        return URI.create(string);
                    }
                    return null;
                }));
    }
}
