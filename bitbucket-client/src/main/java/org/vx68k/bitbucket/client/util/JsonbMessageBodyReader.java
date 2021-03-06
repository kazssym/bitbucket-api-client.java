/*
 * JsonbMessageBodyReader.java
 * Copyright (C) 2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

/**
 * Implementation class of {@link MessageBodyReader} that uses JSON-B
 * for deserialization of an object.
 *
 * @param <T> a type of objects that are to be read
 */
public class JsonbMessageBodyReader<T> implements MessageBodyReader<T>
{
    private final JsonbBuilder jsonbBuilder;

    /**
     * Constructs a {@link JsonbMessageBodyReader} instance.
     *
     * @param jsonbBuilder a {@link JsonbBuilder} object that is to be used
     */
    public JsonbMessageBodyReader(final JsonbBuilder jsonbBuilder)
    {
        this.jsonbBuilder = jsonbBuilder;
    }

    @Override
    public final boolean isReadable(Class<?> type, Type genericType,
        Annotation[] annotations, MediaType mediaType)
    {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public final T readFrom(Class<T> type, Type genericType,
        Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, String> headers, InputStream stream)
        throws IOException
    {
        try (Jsonb jsonb = jsonbBuilder.build()) {
            return jsonb.fromJson(stream, type);
        }
        catch (final Exception e) {
            throw new IOException(e);
        }
    }

}