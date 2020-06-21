/*
 * BitbucketBranch.java
 * Copyright (C) 2018-2020 Kaz Nishimura
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

import java.util.List;

/**
 * Branch, named branch or bookmark in a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public interface BitbucketBranch extends BitbucketRepository.Ref
{
    /**
     *
     * @return the type of the branch
     */
    String getType();

    /**
     *
     * @return the list of the heads of the branch
     */
    List<BitbucketRepository.Commit> getHeads();
}
