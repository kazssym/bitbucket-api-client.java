/*
 * Repository
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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;

/**
 * Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Repository {

    private static final Logger logger = Utilities.getLogger();

    /**
     * Constructs a <em>blank</em> object.
     */
    public Repository() {
        logger.finer("Creating a blank Repository");
    }

    public Repository(JsonObject json) {
        logger.log(Level.INFO, "Parsing Repository JSON object: {0}", json);
        // TODO: Parse the JSON object.
    }
}
