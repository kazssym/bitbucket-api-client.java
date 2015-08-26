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

    /**
     * Constructs a <em>blank</em> object.
     */
    public User() {
        logger.finer("Creating a default User");
    }

    /**
     * Constructs an object from a JSON object.
     *
     * @param json JSON object that represents a Bitbucket user
     */
    public User(JsonObject json) {
        logger.log(Level.INFO, "Parsing User JSON object: {0}", json);
        username = json.getString("username");
        type = json.getString("type", DEFAULT_TYPE);
        uuid = Utilities.parseUuid(json.getString("uuid"));
        displayName = json.getString("display_name");
        try {
            website = Utilities.parseURL(json.getString("website"));
        } catch (MalformedURLException e) {
            logger.log(Level.WARNING, "Could not parse the website", e);
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

    /**
     * Tests whether this object equals to another or not.
     * Equality is tested by {@link UUID}s first, and if they are
     * <code>null</code>, usernames are compared.
     *
     * @param object another object
     * @return <code>true</code> if this object equals to the other object,
     * or <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object object) {
        // Returns <code>true</code> always if the objet is <code>this</code>.
        if (object == this) {
            return true;
        }
        // Then, tests equality if the other object is of the same class.
        if (object != null && object.getClass() == User.class) {
            User user = (User) object;
            if (uuid != null) {
                return uuid.equals(user.getUuid());
            } else if (user.getUuid() != null) {
                return false;
            }
            if (username != null) {
                return username.equals(user.getUsername());
            } else if (user.getUsername() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Computes the hash code for this object.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int code = getClass().hashCode();
        if (uuid != null) {
            code ^= uuid.hashCode();
        } else if (username != null) {
            code ^= username.hashCode();
        }
        return code;
    }
}
