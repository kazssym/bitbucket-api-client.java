/*
 * BitbucketAccountAdapter.java
 * Copyright (C) 2020 Kaz Nishimura
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

package org.vx68k.bitbucket.client.adapter;

import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.adapter.JsonbAdapter;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.client.internal.ClientTeamAccount;
import org.vx68k.bitbucket.client.internal.ClientUserAccount;

public class BitbucketAccountAdapter
        implements JsonbAdapter<BitbucketAccount, Map<String, Object>>
{
    @Override
    public final Map<String, Object> adaptToJson(final BitbucketAccount account)
        throws Exception
    {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            // Stupid but probably the right path.
            return jsonb.<Map<String, Object>>fromJson(jsonb.toJson(account), Map.class);
        }
    }

    @Override
    public final BitbucketAccount adaptFromJson(final Map<String, Object> map)
        throws Exception
    {
        String type = (String)map.get("type");
        if (type != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                if (type.equals("user")) {
                    return jsonb.fromJson(jsonb.toJson(map), ClientUserAccount.class);
                }
                if (type.equals("team")) {
                    return jsonb.fromJson(jsonb.toJson(map), ClientTeamAccount.class);
                }
            }
        }
        return null;
    }
}
