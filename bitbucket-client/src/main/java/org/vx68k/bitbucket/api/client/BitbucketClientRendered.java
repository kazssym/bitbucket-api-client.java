/*
 * BitbucketClientRendered.java
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

import javax.json.JsonObject;
import org.vx68k.bitbucket.BitbucketRendered;

/**
 * Client implementation of {@link BitbucketRendered}.
 * This class represents a rendered text by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientRendered extends BitbucketClientObject implements
    BitbucketRendered
{
    /**
     * Name of the {@code html} value in a JSON rendered text object.
     */
    private static final String HTML = "html";

    /**
     * Name of the {@code markup} value in a JSON rendered text object.
     */
    private static final String MARKUP = "markup";

    /**
     * Name of the {@code raw} value in a JSON rendered text object.
     */
    private static final String RAW = "raw";

    /**
     * Initializes the object.
     *
     * @param jsonObject a JSON object
     */
    public BitbucketClientRendered(final JsonObject jsonObject)
    {
        super(jsonObject);
    }

    @Override
    public final String getMarkup()
    {
        return getJsonObject().getString(MARKUP, null);
    }

    @Override
    public final String getRaw()
    {
        return getJsonObject().getString(RAW, null);
    }

    @Override
    public final String getHtml()
    {
        return getJsonObject().getString(HTML, null);
    }
}
