/*
 * BitbucketAPITest.java - class BitbucketAPITest
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

package org.vx68k.bitbucket.api.client.example;

import org.junit.Before;

/**
 * Unit tests for [@link BitbucketAPI}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketAPITest
{
    /**
     * Test value for the client identifier.
     */
    private static final String CLIENT_ID = "id";

    /**
     * Test value for the client secret.
     */
    private static final String CLIENT_SECRET = "secret";

    /**
     * Clears system properties.
     */
    @Before
    public void setUp()
    {
        System.clearProperty(BitbucketAPI.OAUTH_CLIENT_ID_PROPERTY);
        System.clearProperty(BitbucketAPI.OAUTH_CLIENT_SECRET_PROPERTY);
    }
}
