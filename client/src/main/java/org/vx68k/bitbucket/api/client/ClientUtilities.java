/*
 * ClientUtilities
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Collection of static utility methods.
 * @author Kaz Nishimura
 * @since 4.0
 */
public class ClientUtilities {

    private static final String PACKAGE_NAME =
            ClientUtilities.class.getPackage().getName();

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssXXX");

    protected ClientUtilities() {
    }

    /**
     * Returns a logger.
     * @return logger for this package
     */
    public static Logger getLogger() {
        return Logger.getLogger(
                PACKAGE_NAME, PACKAGE_NAME + ".resources.LogMessages");
    }

    /**
     * Parses a string into a UUID.
     * The string representation of a UUID may be enclosed in braces.
     * @param string string that represents a UUID
     * @return {@link UUID} object, or <code>null</code> if the string is
     * <code>null</code>
     * @throws IllegalArgumentException if the string could not parsed as a
     * UUID.
     */
    public static UUID parseUUID(String string) {
        if (string == null) {
            return null;
        }

        if (string.startsWith("{") && string.endsWith("}")) {
            string = string.substring(1, string.length() - 1);
        }
        return UUID.fromString(string);
    }

    /**
     * Parses a string into a date.
     * @param date string that represents an ISO 8601 date and time
     * @return parsed date, or <code>null</code> if the argument is
     * <code>null</code>
     * @throws IllegalArgumentException if the string could not be parsed as a
     * date
     */
    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }

        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException exception) {
            throw new IllegalArgumentException(
                    "\"" + date + "\" could not be parsed as Date",
                    exception);
        }
    }

    /**
     * Parses a JSON object into a map of links.
     * @param jsonObject JSON object
     * @return map of links
     */
    public static Map<String, URL> parseLinks(JsonObject jsonObject) {
        Map<String, URL> links = new HashMap<String, URL>();
        for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            try {
                URL link = parseLink((JsonObject) entry.getValue());
                links.put(entry.getKey(), link);
            } catch (MalformedURLException exception) {
                getLogger().log(
                        Level.WARNING, "Could not parse a URL value",
                        exception);
            }
        }
        return links;
    }

    /**
     * Parses a JSON object into a URL.
     * @param jsonObject JSON object
     * @return link
     * @throws MalformedURLException if the <code>href</code> value could not
     * be parsed as a URL.
     */
    protected static URL parseLink(JsonObject jsonObject)
            throws MalformedURLException {
        return parseURL(jsonObject.getString(ClientJsonKeys.HREF));
    }

    /**
     * Parses a string into a URL.
     * @param string string that represents a URL
     * @return parsed {@link URL} object, or <code>null</code> if the string
     * is <code>null</code>
     * @throws MalformedURLException if the string could not parsed as a URL.
     */
    public static URL parseURL(String string) throws MalformedURLException {
        if (string == null) {
            return null;
        }

        return new URL(string);
    }
}
