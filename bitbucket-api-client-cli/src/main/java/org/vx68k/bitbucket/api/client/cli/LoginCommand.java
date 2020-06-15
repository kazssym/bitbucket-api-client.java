/*
 * LoginCommand.java
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

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.ws.rs.ClientErrorException;
import org.vx68k.bitbucket.client.BitbucketClient;
import org.vx68k.bitbucket.client.TokenRefreshEvent;
import org.vx68k.bitbucket.client.TokenRefreshListener;

/**
 * CLI {@code login} command.
 *
 * @author Kaz Nishimura
 */
public final class LoginCommand extends AbstractCommand
{
    /**
     * Relative path to the properties file.
     */
    private static final String PROPERTIES = "resources/client.properties";

    /**
     * Initializes the object.
     *
     * @param bitbucketClient a Bitbucket API client
     */
    public LoginCommand(final BitbucketClient bitbucketClient)
    {
        super(bitbucketClient);

        loadClientCredentials();
        CLIUtilities.restoreTokens(bitbucketClient);
        bitbucketClient.addTokenRefreshListener(new TokenRefreshListener()
        {
            @Override
            public void tokenRefreshed(final TokenRefreshEvent event)
            {
                CLIUtilities.saveTokens(bitbucketClient);
            }
        });
    }

    /**
     * Loads the client credentials from a properties file.
     */
    private void loadClientCredentials()
    {
        BitbucketClient bitbucketClient = getBitbucketClient();

        Properties properties = new Properties();
        try (InputStream s = getClass().getResourceAsStream(PROPERTIES)) {
            properties.load(s);
        }
        catch (final IOException exception) {
            // @todo What else can we do?
            System.err.format("Failed to load properties: %s\n", exception);
        }

        String clientId = properties.getProperty("oauth.clientId");
        String clientSecret = properties.getProperty("oauth.clientSecret");
        bitbucketClient.setClientId(clientId);
        bitbucketClient.setClientSecret(clientSecret);
    }

    @Override
    public void run(final String commandName, final String[] args)
    {
        Console console = System.console();
        if (console == null) {
            throw new CLIException(commandName + ": No console");
        }

        String username = console.readLine("Username: ");
        String password = String.valueOf(console.readPassword("Password: "));

        BitbucketClient bitbucketClient = getBitbucketClient();
        try {
            bitbucketClient.login(username, password);
        }
        catch (final ClientErrorException exception) {
            throw new CLIException("Login failed", exception);
        }
        CLIUtilities.saveTokens(bitbucketClient);
    }
}
