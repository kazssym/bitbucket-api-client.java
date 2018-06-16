/*
 * BitbucketClientUtilitiesTest.java - class BitbucketClientUtilitiesTest
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

/**
 * Unit tests for {@link BitbucketClientUtilities}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketClientUtilitiesTest
{
    /**
     * Tests {@link BitbucketClientUtilities#parseURL parseURL}.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testParseURL() throws MalformedURLException
    {
        URL url1 = BitbucketClientUtilities.parseURL(null);
        assertNull(url1);

        URL url2 = BitbucketClientUtilities.parseURL("https://api.bitbucket.org/");
        assertNotNull(url2);
    }

    /**
     * Tests {@link BitbucketClientUtilities#parseUUID parseUUID}.
     */
    @Test
    public void testParseUUID()
    {
        // @todo Write tests.
    }
}