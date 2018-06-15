/*
 * BitbucketEventTest.java - class BitbucketEventTest
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

package org.vx68k.bitbucket.webhook.tests;

import org.junit.Test;
import org.vx68k.bitbucket.webhook.BitbucketEvent;

/**
 * Unit tests for {@link BitbucketEvent}.
 *
 * @author Kaz Nishimura
 */
public final class BitbucketEventTest
{
    /**
     * Tests construction with {@code null}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullObject()
    {
        new BitbucketEvent(null);
    }
}
