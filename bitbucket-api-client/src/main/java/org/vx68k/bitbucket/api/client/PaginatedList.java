/*
 * PaginatedList.java
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

package org.vx68k.bitbucket.api.client;

import java.net.URI;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.function.Function;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Paginated list on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @param <E> type of the elements
 * @since 5.0
 */
public class PaginatedList<E> extends AbstractList<E>
{
    /**
     * Bitbucket client given to the constructor.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * Function to create each element.
     */
    private final Function<JsonObject, ? extends E> creator;

    /**
     * URL of the next page.
     */
    private URI nextPage;

    /**
     * List of the known values.
     */
    private final ArrayList<E> knownValues = new ArrayList<>();

    /**
     * Known size of the list.
     * If this value is less than zero, all the element must be fetched.
     */
    private int knownSize = -1;

    /**
     * Initializes this object.
     *
     * @param firstPage the URI of the first page
     * @param bitbucketClient a Bitbucket API client
     * @param creator a function to create each element
     */
    public PaginatedList(
        final URI firstPage, final BitbucketClient bitbucketClient,
        final Function<JsonObject, ? extends E> creator)
    {
        this.bitbucketClient = bitbucketClient;
        this.creator = creator;
        this.nextPage = firstPage;
    }

    /**
     * Fetches the next page.
     */
    protected final void fetchNext()
    {
        JsonObject object = bitbucketClient.get(nextPage);
        if (knownSize < 0) {
            knownSize = object.getInt("size", -1);
            if (knownSize >= 0) {
                knownValues.ensureCapacity(knownSize);
            }
        }

        JsonArray values = object.getJsonArray("values");
        values.stream()
            .map((value) -> (JsonObject) value)
            .map(creator)
            .forEachOrdered((e) -> knownValues.add(e));

        String next = object.getString("next", null);
        if (next != null) {
            nextPage = URI.create(next);
        }
        else {
            nextPage = null;
            knownSize = knownValues.size();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final E get(final int index)
    {
        while (nextPage != null && index >= knownValues.size()) {
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
        while (nextPage != null && knownSize < 0) {
            fetchNext();
        }
        assert knownSize >= 0;
        return knownSize;
    }
}
