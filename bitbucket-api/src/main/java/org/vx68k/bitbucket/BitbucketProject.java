/*
 * BitbucketProject.java
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

package org.vx68k.bitbucket;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

/**
 * Project on Bitbucket Cloud.
 * A project groups multiple repositories of an owner and every repository
 * belongs to a single project.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public interface BitbucketProject
{
    UUID getUuid();

    String getKey();

    String getName();

    Map<String, URI> getLinks();
}
