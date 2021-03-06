/*
 * ClientRendered.java
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

package org.vx68k.bitbucket.client.internal;

import org.vx68k.bitbucket.BitbucketRendered;

/**
 * Implementation class of {@link BitbucketRendered} for the Bitbucket
 * Cloud REST API.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class ClientRendered implements BitbucketRendered
{
    public static final String RENDERED = "rendered";

    private String type;

    private String markup;

    private String raw;

    private String html;

    /**
     * Constructs a rendered text.
     */
    public ClientRendered()
    {
        // Nothing to do.
    }

    public ClientRendered(final ClientRendered other)
    {
        this.type = other.type;
        this.markup = other.markup;
        this.raw = other.raw;
        this.html = other.html;
    }

    public final String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        if (type != null && !(type.equals(RENDERED))) {
            throw new IllegalArgumentException("Type is not of rendered objects");
        }
        this.type = type;
    }

    @Override
    public final String getMarkup()
    {
        return markup;
    }

    public final void setMarkup(final String markup)
    {
        this.markup = markup;
    }

    @Override
    public final String getRaw()
    {
        return raw;
    }

    public final void setRaw(final String raw)
    {
        this.raw = raw;
    }

    @Override
    public final String getHtml()
    {
        return html;
    }

    public final void setHtml(final String html)
    {
        this.html = html;
    }
}
