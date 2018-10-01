/*
 * BitbucketClientTeam.java - class BitbucketClientTeam
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
import org.vx68k.bitbucket.api.BitbucketTeam;

/**
 * Bitbucket Cloud team account represented by a JSON object.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class BitbucketClientTeam extends BitbucketClientAccount implements
    BitbucketTeam
{
    /**
     * Type value for teams.
     */
    public static final String TEAM_TYPE = "team";

    /**
     * Constructs this object with no Bitbucket client.
     *
     * @param object JSON team object
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not of a team
     */
    public BitbucketClientTeam(final JsonObject object)
    {
        this(object, null);
    }

    /**
     * Constructs this object.
     *
     * @param object JSON team object
     * @param client Bitbucket client, or {@code null}
     * @exception IllegalArgumentException if {@code object} is {@code null} or
     * is not of a team
     */
    public BitbucketClientTeam(
        final JsonObject object, final BitbucketClient client)
    {
        super(object, client);

        String type = getType();
        if (type == null || !type.equals(TEAM_TYPE)) {
            throw new IllegalArgumentException("Not team");
        }
    }
}
