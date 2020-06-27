/*
 * SessionUserTest.java
 * Copyright (C) 2015-2020 Kaz Nishimura
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

package org.vx68k.bitbucket.webapp;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * Unit tests for {@link SessionUser}.
 *
 * @author Kaz Nishimura
 */
public final class SessionUserTest
{
    /**
     * Tests the default constructor.
     */
    @Test
    public void testDefault()
    {
        SessionUser sessionUser = new SessionUser(new BitbucketClient());
        assertFalse(sessionUser.isLoggedIn());
    }
}
