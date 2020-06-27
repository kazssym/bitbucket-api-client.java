/*
 * TeamCommandGroup.java - class TeamCommandGroup
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

import java.io.PrintWriter;
import org.vx68k.bitbucket.BitbucketAccount;
import org.vx68k.bitbucket.client.BitbucketClient;

/**
 * {@code team} command group.
 *
 * @author Kaz Nishimura
 */
public class TeamCommandGroup extends CommandGroup
{
    /**
     * Constructs this object.
     *
     * @param bitbucketClient value for the Bitbucket API client
     */
    public TeamCommandGroup(final BitbucketClient bitbucketClient)
    {
        super(bitbucketClient);

        add("show", new ShowCommand());
        // @todo Add more commands.
    }

    /**
     * {@code show} command.
     */
    final class ShowCommand implements Command
    {
        /**
         * Print format.
         */
        private static final String FORMAT = "%-12s  %s\n";

        /**
         * Prints a team information.
         *
         * @param team team to print
         */
        void print(final BitbucketAccount team)
        {
            PrintWriter out = new PrintWriter(System.out);
            out.format(FORMAT, "Name", team.getUsername());
            out.format(FORMAT, "UUID", team.getUuid());
            out.format(FORMAT, "Display Name", team.getDisplayName());
            out.format(FORMAT, "Website", team.getWebsite());
            out.format(FORMAT, "Location", team.getLocation());
            out.format(FORMAT, "Created", team.getCreated());
            out.flush();
        }

        /**
         * Runs the {@code show} command.
         *
         * @param commandName actual command name
         * @param args command arguments
         */
        @Override
        public void run(final String commandName, final String[] args)
        {
            if (args.length < 1) {
                throw new CLIException("Missing arguments");
            }

            BitbucketAccount team = getBitbucketClient().getTeamAccount(args[0]);
            if (team != null) {
                print(team);
            }
            else {
                System.err.println(commandName + ": Team not found");
            }
        }
    }
}
