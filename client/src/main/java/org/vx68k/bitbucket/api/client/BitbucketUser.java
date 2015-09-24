/*
 * BitbucketUser
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
public class BitbucketUser extends BitbucketEntity {

    /**
     * Type value for users.
     */
    private static final String USER_TYPE = "user";

    // Properties.
    private UUID uuid;
    private String name;
    private String displayName;
    private Map<String, URL> links;
    private URL website;
    private String location;
    private Date created;

    /**
     * Constructs this instance with no property values.
     */
    public BitbucketUser() {
        super(USER_TYPE);
        ClientUtilities.getLogger().finer("Creating a blank User");
    }

    /**
     * Constructs this instance from a JSON object.
     * @param jsonObject JSON object that represents a Bitbucket user
     */
    public BitbucketUser(JsonObject jsonObject) {
        super(jsonObject);
        if (!getEntityType().equals(USER_TYPE)) {
            throw new IllegalArgumentException(
                    "Type is not \"" + USER_TYPE + "\"");
        }
        ClientUtilities.getLogger().log(
                Level.INFO,
                "Parsing JSON object (\"" + USER_TYPE + "\"): {0}",
                jsonObject);
        uuid = ClientUtilities.parseUUID(jsonObject.getString(
                ClientJsonKeys.UUID));
        name = jsonObject.getString(ClientJsonKeys.USERNAME);
        displayName = jsonObject.getString(ClientJsonKeys.DISPLAY_NAME);
        links = ClientUtilities.parseLinks(jsonObject.getJsonObject(
                ClientJsonKeys.LINKS));
        try {
            website = ClientUtilities.parseURL(jsonObject.getString(
                    ClientJsonKeys.WEBSITE, null));
        } catch (MalformedURLException exception) {
            ClientUtilities.getLogger().log(
                    Level.WARNING,
                    "Could not parse the \"" + ClientJsonKeys.WEBSITE
                    + "\" value",
                    exception);
        }
        location = jsonObject.getString(ClientJsonKeys.LOCATION, null);
        created = ClientUtilities.parseDate(jsonObject.getString(
                ClientJsonKeys.CREATED_ON, null));
    }

    /**
     * Returns the UUID.
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the name.
     * @return name
     */
    public String getName() {
        return name;
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
     * Sets the name.
     * @param name name to be set
     */
    public void setName(String name) {
        if (name != null && name.contains("/")) {
            throw new IllegalArgumentException("'/' in name");
        }
        this.name = name;
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
     * Computes the hash code for this instance.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int code = getClass().hashCode();
        if (uuid != null) {
            code ^= uuid.hashCode();
        } else if (name != null) {
            code ^= name.hashCode();
        }
        return code;
    }

    /**
     * Tests whether this instance equals to another one or not.
     * This method tests equality of {@link #uuid}s if either one is not
     * <code>null</code>, otherwise equality of {@link #name}s.
     * @param object other instance
     * @return <code>true</code> if this instance equals to the other one;
     * <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object object) {
        // Returns <code>true</code> always if the objet is <code>this</code>.
        if (object == this) {
            return true;
        }
        // Then, tests equality if the other object is of the same class.
        if (object != null && object.getClass() == getClass()) {
            BitbucketUser that = (BitbucketUser) object;
            if (uuid != null) {
                return uuid.equals(that.uuid);
            } else if (that.uuid != null) {
                return false;
            }
            if (name != null) {
                return name.equals(that.name);
            } else if (that.name != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the name.
     * @return name
     */
    @Override
    public String toString() {
        return getName();
    }
}
