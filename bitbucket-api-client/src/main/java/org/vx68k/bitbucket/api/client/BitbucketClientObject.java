/*
 * BitbucketClientObject.java - class BitbucketClientObject
 * Copyright (C) 2015-2018 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.vx68k.bitbucket.api.client;

import javax.json.JsonObject;

/**
 * Superclass of objects represented by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientObject
{
    /**
     * Name for the {@code type} value in a JSON object.
     */
    private static final String TYPE = "type";

    /**
     * JSON object given to the constructor.
     */
    private final JsonObject jsonObject;

    /**
     * Constructs this object with a JSON object.
     *
     * @param object JSON object
     * @exception IllegalArgumentException if the JSON object is {@code null}
     */
    public BitbucketClientObject(final JsonObject object)
    {
        if (object == null) {
            throw new IllegalArgumentException("JSON object is null");
        }
        jsonObject = object;
    }

    /**
     * Returns the JSON object given to the constructor.
     *
     * @return the JSON object
     */
    public final JsonObject getJsonObject()
    {
        return jsonObject;
    }

    /**
     * Returns the type name of this object.
     *
     * @return the type name, or {@code null} if this object has no type
     */
    public final String getType()
    {
        JsonObject object = getJsonObject();
        return object.getString(TYPE, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int code = getClass().hashCode();
        code ^= jsonObject.hashCode();
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object)
    {
        if (object == this) {
            return true;
        }
        if (object != null && object.getClass() == getClass()) {
            BitbucketClientObject that = (BitbucketClientObject) object;
            if (!jsonObject.equals(that.jsonObject)) {
                return false;
            }
            return true;
        }
        return false;
    }
}
