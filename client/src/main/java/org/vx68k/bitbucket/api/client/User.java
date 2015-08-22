/*
 * User
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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Bitbucket user.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class User {

    private static final String DEFAULT_TYPE = "user";

    /**
     * Username.
     */
    private String username;

    /**
     * Type of this user, normally "user".
     */
    private String type = DEFAULT_TYPE;

    /**
     * UUID of this user.
     */
    private UUID uuid;

    /**
     * Display name of this user.
     */
    private String displayName;

    /**
     * Website URL of this user.
     */
    private URL website;

    /**
     * Map of links about this user.
     */
    private final Map<String, URL> links = new HashMap<String, URL>();

    private static final Logger logger = Utilities.getLogger();

    public User() {
        logger.finer("Creating a default User");
    }

    public User(JsonObject jsonObject) {
        logger.log(Level.INFO, "Parsing User JSON object: {0}", jsonObject);
        username = jsonObject.getString("username");
        type = jsonObject.getString("type", DEFAULT_TYPE);
        uuid = Utilities.parseUuid(jsonObject.getString("uuid"));
        displayName = jsonObject.getString("display_name");
        try {
            website = Utilities.parseURL(jsonObject.getString("website"));
        } catch (MalformedURLException e) {
        }
        // TODO: Parse links.
    }

    public User(InputStream inputStream) {
        this(Json.createReader(inputStream).readObject());
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public URL getWebsite() {
        return website;
    }

    public Map<String, URL> getLinks() {
        return links;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }
}
