/*
 * BitbucketClientObject.java - class BitbucketClientObject
 * Copyright (C) 2015-2018 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package org.vx68k.bitbucket.api.client;

import static org.vx68k.bitbucket.api.client.JsonUtilities.toLink;

import java.util.Objects;
import javax.json.JsonObject;
import javax.ws.rs.core.Link;

/**
 * Common super object represented by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientObject
{
    /**
     * Name for the {@code type} value in a JSON object.
     */
    static final String TYPE = "type";

    /**
     * Name for the {@code links} object in a JSON object.
     */
    private static final String LINKS = "links";

    /**
     * JSON object given to the constructor.
     */
    private final JsonObject jsonObject;

    /**
     * {@link BitbucketClient} object.
     */
    private BitbucketClient bitbucketClient;

    /**
     * Initializes the object with a JSON object.
     *
     * @param jsonObject a JSON object
     * @exception IllegalArgumentException if {@code object} is {@code null}
     */
    public BitbucketClientObject(final JsonObject jsonObject)
    {
        this(jsonObject, null);
    }

    /**
     * Initializes the object with a JSON object and a Bitbucket API client.
     *
     * @param jsonObject a JSON object
     * @param bitbucketClient a Bitbucket API client
     * @exception IllegalArgumentException if {@code object} is {@code null}
     */
    public BitbucketClientObject(
        final JsonObject jsonObject, final BitbucketClient bitbucketClient)
    {
        this.jsonObject = jsonObject;
        this.bitbucketClient = bitbucketClient;

        if (this.jsonObject == null) {
            throw new IllegalArgumentException("JSON object is null");
        }
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
     * Returns the {@link BitbucketClient} object.
     *
     * @return the {@link BitbucketClient} object
     */
    public final BitbucketClient getBitbucketClient()
    {
        return bitbucketClient;
    }

    /**
     * Associates a Bitbucket client.
     *
     * @param value Bitbucket client to associate, or {@code null}
     */
    public final void setBitbucketClient(final BitbucketClient value)
    {
        bitbucketClient = value;
    }

    /**
     * Returns the type name of this object.
     *
     * @return the type name if it was specified in the JSON object;
     * {@code null} otherwise
     */
    public final String getType()
    {
        JsonObject object = getJsonObject();
        return object.getString(TYPE, null);
    }

    /**
     * Returns the link of this object identified by a name.
     *
     * @param name name of the link
     * @return the link if it was specified in the JSON object; {@code null}
     * otherwise
     */
    public final Link getLink(final String name)
    {
        Link value = null;
        JsonObject links = getJsonObject().getJsonObject(LINKS);
        if (links != null) {
            value = toLink(links.getJsonObject(name));
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"designForExtension"})
    @Override
    public int hashCode()
    {
        final int k = 257;
        int value = getClass().hashCode();
        value = k * value + Objects.hashCode(jsonObject);
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"designForExtension"})
    @Override
    public boolean equals(final Object object)
    {
        if (this != object) {
            if (object == null || object.getClass() != getClass()) {
                return false;
            }

            BitbucketClientObject other = (BitbucketClientObject) object;
            if (!Objects.equals(jsonObject, other.jsonObject)) {
                return false;
            }
            if (!Objects.equals(bitbucketClient, other.bitbucketClient)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the object.
     * <p>This implementation just returns a string representation of the
     * underlying JSON object.  Subclasses may override this method to return
     * other values.</p>
     *
     * @return a string representation of the object
     */
    @SuppressWarnings({"designForExtension"})
    @Override
    public String toString()
    {
        return jsonObject.toString();
    }
}