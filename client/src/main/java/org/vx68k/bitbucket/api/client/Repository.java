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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
     * JSON key for the <code>type</code> value that is typically
     * <code>"repository"</code>.
     */
    protected static final String TYPE_JSON_KEY = "type";

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
     * Default type value.
     */
    private static final String DEFAULT_TYPE = "repository";

    private static final Logger logger = Utilities.getLogger();

    private UUID uuid;

    private User owner;

    private String name;

    private String fullName;

    private String type = DEFAULT_TYPE;

    private String scm;

    // Note: <code>private</code> is reserved so capitalized.
    private boolean Private;

    private Map<String, URL> links = new HashMap<String, URL>();

    /**
     * Constructs a <em>blank</em> object.
     */
    public Repository() {
        logger.finer("Creating a blank Repository");
    }

    public Repository(JsonObject json) {
        logger.log(Level.INFO, "Parsing Repository JSON object: {0}", json);
        this.uuid = Utilities.parseUuid(json.getString(UUID_JSON_KEY));
        this.owner = new User(json.getJsonObject(OWNER_JSON_KEY));
        this.name = json.getString(NAME_JSON_KEY);
        this.fullName = json.getString(FULL_NAME_JSON_KEY);
        this.type = json.getString(TYPE_JSON_KEY, DEFAULT_TYPE);
        this.scm = json.getString(SCM_JSON_KEY);
        this.Private = json.getBoolean(IS_PRIVATE_JSON_KEY);
        this.links = Utilities.parseLinks(
                json.getJsonObject(LINKS_JSON_KEY));
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

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
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
