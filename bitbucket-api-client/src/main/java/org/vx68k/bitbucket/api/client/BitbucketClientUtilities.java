/*
 * BitbucketClientUtilities.java - class BitbucketClientUtilities
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

package org.vx68k.bitbucket.api.client;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.ws.rs.core.Link;

/**
 * Collection of static utility methods.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientUtilities
{
    /**
     * Name for the {@code href} value in a JSON link object.
     */
    private static final String HREF = "href";

    /**
     * Does nothing but denies direct instantiation.
     */
    protected BitbucketClientUtilities()
    {
    }

    /**
     * Converts a JSON date-time string to an instant.
     * The date-time string may have a time offset.
     *
     * @param string JSON date-time string, or {@code null}
     * @return {@link Instant} object if {@code string} is not {@code null},
     * {@code null} otherwise
     */
    public static Instant toInstant(final JsonString string)
    {
        Instant value = null;
        if (string != null) {
            value = OffsetDateTime.parse(string.getString()).toInstant();
        }
        return value;
    }

    /**
     * Converts a JSON string to a UUID.
     * The string may enclose a UUID in a pair of braces.
     *
     * @param string JSON string, or {@code null}
     * @return {@link UUID} object if {@code string} is not {@code null},
     * {@code null} otherwise
     */
    public static UUID toUUID(final JsonString string)
    {
        UUID value = null;
        if (string != null) {
            String uuidString = string.getString();
            if (uuidString.startsWith("{") && uuidString.endsWith("}")) {
                uuidString = uuidString.substring(1, uuidString.length() - 1);
            }
            value = UUID.fromString(uuidString);
        }
        return value;
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
