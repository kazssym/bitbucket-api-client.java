/*
 * JsonMessageBodyReader.java - class JsonMessageBodyReader
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.client.util;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

/**
 * {@link MessageBodyReader} for {@link JsonStructure} objects.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@Consumes({MediaType.APPLICATION_JSON})
public final class JsonStructureMessageBodyReader
    implements MessageBodyReader<JsonStructure>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadable(final Class<?> type,
        final Type genericType, final Annotation[] annotations,
        final MediaType mediaType)
    {
        return JsonStructure.class.isAssignableFrom(type)
            && MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonStructure readFrom(final Class<JsonStructure> type,
        final Type genericType, final Annotation[] annotations,
        final MediaType mediaType,
        final MultivaluedMap<String, String> httpHeaders,
        final InputStream entityStream)
    {
        try (JsonReader reader = Json.createReader(entityStream)) {
            return reader.read();
        }
    }
}
