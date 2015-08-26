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
     * JSON key for the uuid value.
     */
    protected static final String UUID_KEY = "uuid";

    /**
     * JSON key for the owner object.
     */
    protected static final String OWNER_KEY = "owner";

    /**
     * JSON key for the name value.
     */
    protected static final String NAME_KEY = "name";

    /**
     * JSON key for the fullName value.
     */
    protected static final String FULL_NAME_KEY = "full_name";

    /**
     * JSON key for the type value that is normally <code>"repository"</code>.
     */
    protected static final String TYPE_KEY = "type";

    /**
     * JSON key for the scm value that is either <code>"git"</code> or
     * <code>"hg"</code>.
     */
    protected static final String SCM_KEY = "scm";

    /**
     * JSON key for the private value.
     */
    private static final String IS_PRIVATE_KEY = "is_private";

    /**
     * JSON key for the links object.
     */
    private static final String LINKS_KEY = "links";

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

    private boolean Private;

    private final Map<String, URL> links = new HashMap<String, URL>();

    /**
     * Constructs a <em>blank</em> object.
     */
    public Repository() {
        logger.finer("Creating a blank Repository");
    }

    public Repository(JsonObject json) {
        logger.log(Level.INFO, "Parsing Repository JSON object: {0}", json);
        this.uuid = Utilities.parseUuid(json.getString(UUID_KEY));
        this.owner = new User(json.getJsonObject(OWNER_KEY));
        this.name = json.getString(NAME_KEY);
        this.fullName = json.getString(FULL_NAME_KEY);
        this.type = json.getString(TYPE_KEY, DEFAULT_TYPE);
        this.scm = json.getString(SCM_KEY);
        this.Private = json.getBoolean(IS_PRIVATE_KEY);
        // TODO: Parse the links.
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
}
