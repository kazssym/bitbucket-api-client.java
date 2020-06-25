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

import javax.json.bind.adapter.JsonbAdapter;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.BitbucketTeamAccount;
import org.vx68k.bitbucket.BitbucketUserAccount;
import org.vx68k.bitbucket.client.internal.ClientAccount;
import org.vx68k.bitbucket.client.internal.ClientTeamAccount;
import org.vx68k.bitbucket.client.internal.ClientUserAccount;

public class BitbucketAccountAdapter
        implements JsonbAdapter<BitbucketAccount, ClientAccount>
{
    @Override
    public final ClientAccount adaptToJson(final BitbucketAccount account)
    {
        if (account instanceof ClientAccount) {
            return (ClientAccount)account;
        }
        if (account instanceof BitbucketUserAccount) {
            return new ClientUserAccount((BitbucketUserAccount)account);
        }
        if (account instanceof BitbucketTeamAccount) {
            return new ClientTeamAccount((BitbucketTeamAccount)account);
        }
        throw new IllegalArgumentException("Account is not either of user or of team");
    }

    @Override
    public final BitbucketAccount adaptFromJson(final ClientAccount account)
    {
        return account;
    }
}