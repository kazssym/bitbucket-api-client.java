/*
 * CommandGroup.java - class CommandGroup
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * Group of commands.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class CommandGroup implements Command
{
    /**
     * Bitbucket API client.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * Name-to-command map.
     */
    private final Map<String, Command> commandMap;

    /**
     * Constructs this object.
     *
     * @param bitbucketClient value for the Bitbucket API client
     */
    public CommandGroup(final BitbucketClient bitbucketClient)
    {
        this.bitbucketClient = bitbucketClient;
        commandMap = new HashMap<>();
    }

    /**
     * Returns the Bitbucket API client.
     *
     * @return the Bitbucket API client
     */
    public final BitbucketClient getBitbucketClient()
    {
        return bitbucketClient;
    }

    /**
     * Add a command.
     *
     * @param name command name
     * @param command command
     * @return the modified object
     */
    public final CommandGroup add(final String name, final Command command)
    {
        commandMap.put(name, command);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void run(final String commandName, final String[] args)
    {
        if (args.length == 0) {
            System.err.printf("%s: Missing subcommand\n", commandName);
        }
        else if (commandMap.containsKey(args[0])) {
            Command command = commandMap.get(args[0]);
            command.run(args[0], Arrays.copyOfRange(args, 1, args.length));
        }
    }
}
