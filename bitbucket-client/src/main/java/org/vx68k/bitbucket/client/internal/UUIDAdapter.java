/*
 * UUIDAdapter.java
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

package org.vx68k.bitbucket.client.internal;

import java.util.UUID;
import javax.json.bind.adapter.JsonbAdapter;
import org.vx68k.bitbucket.client.JsonUtilities;

public class UUIDAdapter implements JsonbAdapter<UUID, String>
{
    @Override
    public final String adaptToJson(final UUID uuid)
    {
        return uuid.toString();
    }

    @Override
    public final UUID adaptFromJson(final String string)
    {
        return JsonUtilities.toUUID(string);
    }
}