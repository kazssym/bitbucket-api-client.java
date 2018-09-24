/*
 * CLI.java - class CLI
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

import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * Main class of the CLI.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
public class CLI extends CommandGroup
{
    /**
     * Bitbucket client.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * Constructs this object.
     *
     * @param bitbucketClientValue value for the Bitbucket client
     */
    public CLI(final BitbucketClient bitbucketClientValue)
    {
        bitbucketClient = bitbucketClientValue;

        add("user", new UserCommandGroup());
        // @todo Add commands here.
    }

    /**
     * Command entry point.
     *
     * @param args command arguments
     */
    public static void main(final String[] args)
    {
        CLI cli = new CLI(new BitbucketClient());
        cli.run("CLI", args);
    }
}
