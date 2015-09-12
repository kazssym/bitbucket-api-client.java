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
import java.util.Date;
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
     * Type value for users.
     */
    private static final String USER_TYPE = "user";

    // Properties.
    private UUID uuid;
    private String username;
    private String displayName;
    private Map<String, URL> links;
    private URL website;
    private String location;
    private Date created;

    /**
     * Constructs this object with no property values.
     */
    public User() {
        super(USER_TYPE);
        Utilities.getLogger().finer("Creating a blank User");
    }

    /**
     * Constructs this object from a JSON object.
     * @param jsonObject JSON object that represents a Bitbucket user
     */
    public User(JsonObject jsonObject) {
        super(jsonObject);
        if (!getType().equals(USER_TYPE)) {
            throw new IllegalArgumentException(
                    "Type is not \"" + USER_TYPE + "\"");
        }
        Utilities.getLogger().log(
                Level.INFO,
                "Parsing JSON object (\"" + USER_TYPE + "\"): {0}",
                jsonObject);
        uuid = Utilities.parseUuid(jsonObject.getString(JsonKeys.UUID));
        username = jsonObject.getString(JsonKeys.USERNAME);
        displayName = jsonObject.getString(JsonKeys.DISPLAY_NAME);
        try {
            links = Utilities.parseLinks(jsonObject.getJsonObject(
                    JsonKeys.LINKS));
        } catch (MalformedURLException exception) {
            Utilities.getLogger().log(
                    Level.WARNING,
                    "Could not parse the \"" + JsonKeys.LINKS + "\" object",
                    exception);
        }
        try {
            website = Utilities.parseURL(jsonObject.getString(
                    JsonKeys.WEBSITE, null));
        } catch (MalformedURLException exception) {
            Utilities.getLogger().log(
                    Level.WARNING,
                    "Could not parse the \"" + JsonKeys.WEBSITE + "\" value",
                    exception);
        }
        location = jsonObject.getString(JsonKeys.LOCATION, null);
        // TODO: Parse the <code>created_on</code> value.
    }

    /**
     * Returns the UUID.
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the username.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the display name.
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the map of the links.
     * @return map of the links
     */
    public Map<String, URL> getLinks() {
        return links;
    }

    /**
     * Returns the URL of the website.
     * This property is optional and may be <code>null</code>.
     * @return URL of the website.
     */
    public URL getWebsite() {
        return website;
    }

    /**
     * Returns the location.
     * This property is optional and may be <code>null</code>.
     * @return location
     * @since 4.0
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the date when the user was created.
     * This property is optional and may be <code>null</code>.
     * @return date of creation
     * @since 4.0
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets the UUID.
     * @param uuid UUID to be set
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Sets the username.
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the display name.
     * @param displayName display name to be set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the map of the links.
     * @param links map of the links to be set
     */
    public void setLinks(Map<String, URL> links) {
        this.links = links;
    }

    /**
     * Sets the URL of the website.
     * @param website URL of the website to be set
     */
    public void setWebsite(URL website) {
        this.website = website;
    }

    /**
     * Sets the location.
     * @param location location to be set
     * @since 4.0
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the date when the user was created.
     * @param created date of creation to be set
     * @since 4.0
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Tests whether this object equals to another or not.
     * Equality is tested by UUIDs first, and if they are <code>null</code>,
     * usernames are compared.
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
