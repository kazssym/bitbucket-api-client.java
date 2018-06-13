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

import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import javax.json.JsonObject;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Bitbucket repository.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Repository extends BitbucketClientObject {

    /**
     * Type value for repositories.
     */
    private static final String REPOSITORY_TYPE = "repository";

    // Properties.
    // Note: Since <code>private</code> is reserved, it is capitalized.
    private UUID uuid;
    private BitbucketUser owner;
    private String name;
    private String fullName;
    private String scm;
    private boolean Private;
    private Map<String, URL> links;

    /**
     * Constructs this object with no property values.
     */
    public Repository() {
        super(REPOSITORY_TYPE);
        ClientUtilities.getLogger().finer("Creating a blank Repository");
    }

    public Repository(final JsonObject jsonObject) {
        super(jsonObject);
        if (!getType().equals(REPOSITORY_TYPE)) {
            throw new IllegalArgumentException(
                    "Type is not \"" + REPOSITORY_TYPE + "\"");
        }
        ClientUtilities.getLogger().log(
                Level.INFO,
                "Parsing JSON object (\"" + REPOSITORY_TYPE + "\"): {0}",
                jsonObject);
        uuid = ClientUtilities.parseUUID(jsonObject.getString(
                ClientJsonKeys.UUID));
        owner = new BitbucketClientUser(jsonObject.getJsonObject(
                ClientJsonKeys.OWNER));
        name = jsonObject.getString(ClientJsonKeys.NAME);
        fullName = jsonObject.getString(ClientJsonKeys.FULL_NAME);
        scm = jsonObject.getString(ClientJsonKeys.SCM);
        Private = jsonObject.getBoolean(ClientJsonKeys.IS_PRIVATE);
        links = ClientUtilities.parseLinks(jsonObject.getJsonObject(
                ClientJsonKeys.LINKS));
    }

    /**
     * Returns the UUID.
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the owner.
     * @return owner
     */
    public BitbucketUser getOwner() {
        return owner;
    }

    /**
     * Returns the name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the full name.
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the SCM.
     * @return SCM, which is either <code>"git"</code> or <code>"hg"</code>
     */
    public String getScm() {
        return scm;
    }

    /**
     * Returns the boolean value that indicates whether the repository is
     * private or not.
     * @return <code>true</code> if the repository is private, or
     * <code>false</code> otherwise
     */
    public boolean isPrivate() {
        return Private;
    }

    /**
     * Returns the map of the links.
     * @return map of the links.
     */
    public Map<String, URL> getLinks() {
        return links;
    }

    /**
     * Sets the UUID.
     * @param value UUID to be set
     */
    public void setUuid(final UUID value) {
        uuid = value;
    }

    /**
     * Sets the owner.
     * This method shall also set the full name if is can be derived.
     * @param value owner to be set
     */
    public void setOwner(final BitbucketUser value) {
        owner = value;
        updateFullName();
    }

    /**
     * Sets the name.
     * This method shall also set the full name if is can be derived.
     * @param value to be set
     */
    public void setName(final String value) {
        if (value.contains("/")) {
            throw new IllegalArgumentException(
                    "Repository name must not contain a \"/\"");
        }
        name = value;
        updateFullName();
    }

    /**
     * Sets the SCM
     * @param value SCM to be set, which should be either <code>"git"</code> or
     * <code>"hg"</code>
     */
    public void setScm(final String value) {
        scm = value;
    }

    /**
     * Sets the boolean value that indicates whether the repository is private
     * or not.
     * @param value <code>true</code> if the repository is private, or
     * <code>false</code> otherwise
     */
    public void setPrivate(final boolean value) {
        Private = value;
    }

    /**
     * Sets the map of the links.
     * @param value map of the links to be set
     */
    public void setLinks(final Map<String, URL> value) {
        links = value;
    }

    /**
     * Updated the full name from the owner and the name.
     */
    protected void updateFullName() {
        if (owner != null && name != null && owner.getName() != null) {
            fullName = owner.getName() + "/" + name;
        }
    }
}
