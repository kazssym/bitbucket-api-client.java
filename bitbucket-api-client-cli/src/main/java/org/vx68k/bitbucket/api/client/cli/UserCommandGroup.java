/*
 * UserCommandGroup.java - class UserCommandGroup
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

package org.vx68k.bitbucket.api.client.cli;

/**
 * {@code user} command group.
 *
 * @author Kaz Nishimura
 */
public class UserCommandGroup extends CommandGroup
{
    /**
     * Constructs this object.
     */
    public UserCommandGroup()
    {
        add("show", new UserShowCommand());
        // @todo Add more commands
    }

    /**
     * {@code user show} command.
     */
    public static class UserShowCommand implements Command
    {
        @Override
        public void run(final String name, final String[] args)
        {
            if (args.length >= 1) {
                System.err.println("This command is not implemented yet.");
            }
            else {
                System.err.println(name + ": Missing parameter");
            }
        }
    }
}
