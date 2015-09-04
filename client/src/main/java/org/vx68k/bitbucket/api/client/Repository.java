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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import javax.json.JsonObject;

/**
 * Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Repository extends Entity {

    /**
     * JSON key for the <code>uuid</code> value.
     */
    protected static final String UUID_JSON_KEY = "uuid";

    /**
     * JSON key for the <code>owner</code> object.
     */
    protected static final String OWNER_JSON_KEY = "owner";

    /**
     * JSON key for the <code>name</code> value.
     */
    protected static final String NAME_JSON_KEY = "name";

    /**
     * JSON key for the <code>full_name</code> value.
     */
    protected static final String FULL_NAME_JSON_KEY = "full_name";

    /**
     * JSON key for the <code>scm</code> value that is either
     * <code>"git"</code> or <code>"hg"</code>.
     */
    protected static final String SCM_JSON_KEY = "scm";

    /**
     * JSON key for the <code>is_private</code> value.
     */
    protected static final String IS_PRIVATE_JSON_KEY = "is_private";

    /**
     * JSON key for the <code>links</code> object.
     */
    protected static final String LINKS_JSON_KEY = "links";

    /**
     * Type value for repositories.
     */
    private static final String REPOSITORY_TYPE = "repository";

    private UUID uuid;

    private User owner;

    private String name;

    private String fullName;

    private String scm;

    // Note: <code>private</code> is reserved so capitalized.
    private boolean Private;

    private Map<String, URL> links;

    /**
     * Constructs a <em>blank</em> object.
     */
    public Repository() {
        super(REPOSITORY_TYPE);
        Utilities.getLogger().finer("Creating a blank Repository");
    }

    public Repository(JsonObject json) {
        super(json);
        if (!getType().equals(REPOSITORY_TYPE)) {
            throw new IllegalArgumentException("Not user");
        }
        Utilities.getLogger().log(
                Level.INFO, "Parsing Repository JSON object: {0}", json);
        this.uuid = Utilities.parseUuid(json.getString(UUID_JSON_KEY));
        this.owner = new User(json.getJsonObject(OWNER_JSON_KEY));
        this.name = json.getString(NAME_JSON_KEY);
        this.fullName = json.getString(FULL_NAME_JSON_KEY);
        this.scm = json.getString(SCM_JSON_KEY);
        this.Private = json.getBoolean(IS_PRIVATE_JSON_KEY);
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

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getScm() {
        return scm;
    }

    public boolean isPrivate() {
        return Private;
    }

    public Map<String, URL> getLinks() {
        return links;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setScm(String scm) {
        this.scm = scm;
    }

    public void setPrivate(boolean Private) {
        this.Private = Private;
    }

    public void setLinks(Map<String, URL> links) {
        this.links = links;
    }
}
