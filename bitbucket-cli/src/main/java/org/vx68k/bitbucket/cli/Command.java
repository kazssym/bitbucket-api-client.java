/*
 * Command.java - interface Command
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

package org.vx68k.bitbucket.cli;

/**
 * Interface for commands.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public interface Command
{
    /**
     * Runs the command.
     *
     * @param name invocation name of the command
     * @param args command arguments
     */
    void run(String name, String[] args);

    /**
     * Returns the description of the command.
     * This method is used to make a list of commands.
     * <p>The default implementation returns {@code "(undocumented)"}.</p>
     *
     * @return description of the command
     */
    default String getDescription()
    {
        return "(undocumented)";
    }
}
