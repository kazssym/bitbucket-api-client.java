/*
 * BitbucketEntity
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

import javax.json.JsonObject;

/**
 * Superclass of Bitbucket entities.
 * @author Kaz Nishimura
 * @since 5.0
 */
public abstract class BitbucketEntity {

    private final String entityType;

    /**
     * Constructs this instance with an entity type.
     * @param entityType entity type
     * @throws NullPointerException if the entity type was <code>null</code>
     */
    protected BitbucketEntity(String entityType) {
        if (entityType == null) {
            throw new NullPointerException("The entity type is null");
        }
        this.entityType = entityType;
    }

    /**
     * Constructs this instance with the entity type in a JSON object.
     * @param jsonObject JSON object
     */
    protected BitbucketEntity(JsonObject jsonObject) {
        this(jsonObject.getString(JsonKeys.TYPE));
    }

    public String getEntityType() {
        return entityType;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object != null && object.getClass() == getClass()) {
            BitbucketEntity that = (BitbucketEntity) object;
            if (!entityType.equals(that.entityType)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int code = getClass().hashCode();
        code ^= entityType.hashCode();
        return code;
    }
}
