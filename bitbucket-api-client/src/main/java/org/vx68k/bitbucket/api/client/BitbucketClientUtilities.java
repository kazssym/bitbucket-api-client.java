/*
 * BitbucketClientUtilities.java - class BitbucketClientUtilities
 * Copyright (C) 2015-2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client;

import java.util.UUID;
import javax.json.JsonString;

/**
 * Collection of static utility methods.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientUtilities
{
    /**
     * JSON key for the <code>href</code> value.
     */
    private static final String HREF = "href";

    /**
     * Does nothing but denies direct instantiation.
     */
    protected BitbucketClientUtilities()
    {
    }

    /**
     * Parses a UUID in a JSON string.
     * The string representation of a UUID may be enclosed in braces.
     *
     * @param jsonString JSON string, or {@code null}
     * @return {@link UUID} object, or {@code null}
     * @exception IllegalArgumentException if the JSON string did not represent
     * a UUID
     */
    public static UUID parseUUID(final JsonString jsonString)
        throws IllegalArgumentException
    {
        UUID uuid = null;
        if (jsonString != null) {
            String s = jsonString.getString();
            if (s.startsWith("{") && s.endsWith("}")) {
                s = s.substring(1, s.length() - 1);
            }
            uuid = UUID.fromString(s);
        }
        return uuid;
    }
}
