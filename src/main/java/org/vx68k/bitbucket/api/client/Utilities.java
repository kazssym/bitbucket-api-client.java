/*
 * Utilities
 * Copyright (C) 2015 Nishimura Software Studio
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.vx68k.bitbucket.api.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Collection of static utility methods for this library.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Utilities {

    /**
     * Returns a logger with the name of this package.
     *
     * @return logger
     */
    public static Logger getLogger() {
        return Logger.getLogger(Utilities.class.getPackage().getName());
    }

    /**
     * Parses a string into a URL.
     * @param string string that represents a URL, or <code>null</code>
     * @return parsed {@link URL} object, or <code>null</code> if the string
     * is <code>null</code>
     * @throws MalformedURLException if the string does not represents a valid
     * URL.
     */
    public static URL parseURL(String string) throws MalformedURLException {
        if (string == null) {
            return null;
        }

        return new URL(string);
    }

    /**
     * Parses a string into a UUID.
     * The string representation of a UUID may be enclosed in braces.
     *
     * @param string string that represents a UUID
     * @return parsed {@link UUID} object, or <code>null</code> if the string
     * is <code>null</code>
     */
    public static UUID parseUuid(String string) {
        if (string == null) {
            return null;
        }

        if (string.startsWith("{") && string.endsWith("}")) {
            string = string.substring(1, string.length() - 1);
        }
        return UUID.fromString(string);
    }
}
