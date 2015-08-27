/*
 * Constants
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
 * Collection of constant values.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Constants {

    private Constants() {
    }

    /**
     * Property name for the Bitbucket client identifider (or consumer key).
     */
    public static final String BITBUCKET_CLIENT_ID_PROPERTY_NAME =
            "org.vx68k.bitbucket.api.client.example.id";

    /**
     * Property name for the Bitbucket client secret (or consumer secret).
     */
    public static final String BITBUCKET_CLIENT_SECRET_PROPERTY_NAME =
            "org.vx68k.bitbucket.api.client.example.secret";
}
