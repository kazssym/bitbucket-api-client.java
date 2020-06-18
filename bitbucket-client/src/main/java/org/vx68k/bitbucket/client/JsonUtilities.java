/*
 * JsonUtilities.java
 * Copyright (C) 2015-2018 Kaz Nishimura
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

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.core.Link;

/**
 * Collection of static utility methods.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class JsonUtilities
{
    /**
     * Name for the {@code href} value in a JSON link object.
     */
    private static final String HREF = "href";

    /**
     * Does nothing but denies direct instantiation.
     */
    protected JsonUtilities()
    {
    }

    /**
     * Converts a JSON date-time string to an instant.
     * The date-time string may have a time offset.
     *
     * @param json a JSON value, or {@code null}
     * @return {@link Instant} object if {@code string} is not null,
     * {@code null} otherwise
     * @exception ClassCastException if {@code json} is not a JSON string or a
     * JSON null.
     */
    public static Instant toInstant(final JsonValue json)
    {
        if (json != null && json.getValueType() != JsonValue.ValueType.NULL) {
            String string = ((JsonString) json).getString();
            return OffsetDateTime.parse(string).toInstant();
        }
        return null;
    }

    /**
     * Converts a JSON string to a UUID.
     * The string may enclose a UUID in a pair of braces.
     *
     * @param json a JSON value, or {@code null}
     * @return {@link UUID} object if {@code json} is not null,
     * {@code null} otherwise
     * @exception ClassCastException if {@code json} is not a JSON string or a
     * JSON null.
     */
    public static UUID toUUID(final JsonValue json)
    {
        if (json != null && json.getValueType() != JsonValue.ValueType.NULL) {
            String string = ((JsonString) json).getString();
            if (string.startsWith("{") && string.endsWith("}")) {
                string = string.substring(1, string.length() - 1);
            }
            return UUID.fromString(string);
        }
        return null;
    }
    /**
     * Converts a JSON string to a UUID.
     * The string may enclose a UUID in a pair of braces.
     *
     * @param json a JSON value, or {@code null}
     * @return {@link UUID} object if {@code json} is not null,
     * {@code null} otherwise
     * @exception ClassCastException if {@code json} is not a JSON string or a
     * JSON null.
     */
    public static UUID toUUID(String string)
    {
        if (string != null) {
            if (string.startsWith("{") && string.endsWith("}")) {
                string = string.substring(1, string.length() - 1);
            }
            return UUID.fromString(string);
        }
        return null;
    }

    /**
     * Converts a JSON object to a link.
     *
     * @param object JSON object, or {@code null}.
     * @return {@link Link} object if {@code object} represents a valid link;
     * {@code null} otherwise.
     */
    public static Link toLink(final JsonObject object)
    {
        Link value = null;
        if (object != null && object.containsKey(HREF)) {
            value = Link.fromUri(object.getString(HREF)).build();
        }
        return value;
    }
}
