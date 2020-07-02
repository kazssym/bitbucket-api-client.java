/*
 * PaginatedList.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client;

import java.net.URI;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Paginated list on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @param <T> the type of the elements
 * @see <a href="https://developer.atlassian.com/bitbucket/api/2/reference/meta/pagination"
 * >Pagination</a>
 * @since 6.0
 */
public class PaginatedList<T> extends AbstractList<T>
{
    private final ClientBuilder clientBuilder;

    /**
     * URI of the next page.
     */
    private URI next;

    /**
     * Runtime type.
     */
    private final Class<? extends T> type;

    private final JsonbBuilder jsonbBuilder;

    /**
     * List of the known values.
     */
    private final List<T> knownValues = new LinkedList<>();

    /**
     * Known size of the list.
     * If this value is less than zero, all the element must be fetched.
     */
    private int knownSize = -1;

    /**
     * Initializes this object.
     *
     * @param clientBuilder a JAX-RS client builder object
     * @param next the URI of the first page
     * @param type the runtime type of the values
     */
    public PaginatedList(final ClientBuilder clientBuilder, final URI next,
        final Class<? extends T> type)
    {
        this.clientBuilder = clientBuilder;
        this.next = next;
        this.type = type;
        this.jsonbBuilder = JsonbBuilder.newBuilder();
    }

    /**
     * Fetches the next page.
     */
    protected final void fetchNext()
    {
        JsonObject json;
        Client client = clientBuilder.build();
        try {
            WebTarget target = client.target(next);
            json = target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonObject.class);
        }
        finally {
            client.close();
        }

        if (knownSize < 0) {
            knownSize = json.getInt("size", -1);
        }
        try (Jsonb jsonb = jsonbBuilder.build()) {
            JsonArray values = json.getJsonArray("values");
            values.stream()
                .map((t) -> jsonb.fromJson(jsonb.toJson(t), type))
                .forEachOrdered(knownValues::add);
        }
        catch (final RuntimeException e) {
            throw e;
        }
        catch (final Exception e) {
            throw new IllegalStateException(e);
        }

        String nextPageUri = json.getString("next", null);
        if (nextPageUri != null) {
            next = URI.create(nextPageUri);
        }
        else {
            next = null;
            knownSize = knownValues.size();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T get(final int index)
    {
        while (next != null && index >= knownValues.size()) {
            fetchNext();
        }
        return knownValues.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int size()
    {
        while (next != null && knownSize < 0) {
            fetchNext();
        }
        assert knownSize >= 0;
        return knownSize;
    }
}
