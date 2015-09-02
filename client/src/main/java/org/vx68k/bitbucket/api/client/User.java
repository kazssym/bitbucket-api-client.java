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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import javax.json.JsonObject;

/**
 * Bitbucket user.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class User extends Entity {

    /**
     * JSON key for the <code>uuid</code> value.
     */
    protected static final String UUID_JSON_KEY = "uuid";

    /**
     * JSON key for the <code>username</code> value.
     */
    protected static final String USERNAME_JSON_KEY = "username";

    /**
     * JSON key for the <code>display_name</code> value.
     */
    protected static final String DISPLAY_NAME_JSON_KEY = "display_name";

    /**
     * JSON key for the <code>website</code> value.
     */
    protected static final String WEBSITE_JSON_KEY = "website";

    /**
     * JSON key for the <code>links</code> object.
     */
    protected static final String LINKS_JSON_KEY = "links";

    /**
     * Type value for users.
     */
    private static final String USER_TYPE = "user";

    /**
     * UUID of this user.
     */
    private UUID uuid;

    /**
     * Username.
     */
    private String username;

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
    private Map<String, URL> links;

    /**
     * Constructs a <em>blank</em> object.
     */
    public User() {
        super(USER_TYPE);
        Utilities.getLogger().finer("Creating a blank User");
    }

    /**
     * Constructs an object from a JSON object.
     * @param json JSON object that represents a Bitbucket user
     */
    public User(JsonObject json) {
        super(json);
        if (!getType().equals(USER_TYPE)) {
            throw new IllegalArgumentException("Not user");
        }
        Utilities.getLogger().log(
                Level.INFO, "Parsing User JSON object: {0}", json);
        this.uuid = Utilities.parseUuid(json.getString(UUID_JSON_KEY));
        this.username = json.getString(USERNAME_JSON_KEY);
        this.displayName = json.getString(DISPLAY_NAME_JSON_KEY);
        try {
            this.website = Utilities.parseURL(
                    json.getString(WEBSITE_JSON_KEY, null));
        } catch (MalformedURLException exception) {
            Utilities.getLogger().log(
                    Level.WARNING, "Could not parse the \"website\" value",
                    exception);
        }
        try {
            this.links = Utilities.parseLinks(
                    json.getJsonObject(LINKS_JSON_KEY));
        } catch (MalformedURLException exception) {
            Utilities.getLogger().log(
                    Level.WARNING, "Could not parse the \"links\" object",
                    exception);
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public void setLinks(Map<String, URL> links) {
        this.links = links;
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
