/*
 * BitbucketClientPaginatedList.java - class BitbucketClientPaginatedList
 * Copyright (C) 2018 Kaz Nishimura
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

import java.util.AbstractList;
import java.util.List;

/**
 * Paginated list on Bitbucket Cloud.
 *
 * @author Kaz Nishimura
 * @param <E> type of the elements
 * @since 5.0
 */
public class BitbucketClientPaginatedList<E extends BitbucketClientObject>
    extends AbstractList<E>
{
    /**
     * Bitbucket client given to the constructor.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * URL for the next page.
     */
    private String nextURL;

    /**
     * Known size of the list.
     * If this value is less than zero, all the element must be fetched.
     */
    private int knownSize;

    /**
     * List of the known values.
     */
    private List<E> knownValues;

    /**
     * Constructs this object.
     *
     * @param client Bitbucket client
     * @param firstURL first URL to fetch
     */
    public BitbucketClientPaginatedList(
        final BitbucketClient client, final String firstURL)
    {
        bitbucketClient = client;
        // @todo Rethink the procedure.
        nextURL = firstURL;
        knownSize = -1;
        knownValues = null;
    }

    /**
     * Returns the Bitbucket client given to the constructor.
     *
     * @return the Bitbucket client
     */
    public final BitbucketClient getBitbucketClient()
    {
        return bitbucketClient;
    }

    /**
     * Fetches the next page.
     */
    protected final void fetchNext()
    {
        // @todo Fetch the next page through {@link BitbucketClient}.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final E get(final int index)
    {
        while (index >= knownValues.size() && nextURL != null) {
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
        while (knownSize < 0) {
            assert nextURL != null;
            fetchNext();
        }
        return knownSize;
    }
}
