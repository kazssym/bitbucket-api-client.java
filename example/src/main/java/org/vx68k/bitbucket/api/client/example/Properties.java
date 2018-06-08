/*
 * Properties
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

package org.vx68k.bitbucket.api.client.example;

/**
 * Collection of constant property keys.
 * @author Kaz Nishimura
 * @since 3.0
 */
public class Properties {

    /**
     * Property prefix.
     */
    private static final String PREFIX =
            "org.vx68k.bitbucket.api.client.example";

    /**
     * Property key for the Bitbucket OAuth client identifider (consumer key).
     */
    public static final String BITBUCKET_OAUTH_CLIENT_ID =
            PREFIX + ".oauth.clientId";

    /**
     * Property key for the Bitbucket OAuth client secret (consumer secret).
     */
    public static final String BITBUCKET_OAUTH_CLIENT_SECRET =
            PREFIX + ".oauth.clientSecret";

    /**
     * Property key for the Google Analytics tracking ID.
     */
    public static final String GOOGLE_ANALYTICS_TRACKING_ID =
            PREFIX + ".analytics.id";
}
