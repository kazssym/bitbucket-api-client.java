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
import java.time.Instant;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.ws.rs.ClientErrorException;
import org.vx68k.bitbucket.api.client.BitbucketClient;
import org.vx68k.bitbucket.api.client.TokenRefreshEvent;
import org.vx68k.bitbucket.api.client.TokenRefreshListener;

/**
 * CLI {@code login} command.
 *
 * @author Kaz Nishimura
 */
public final class LoginCommand implements Command, TokenRefreshListener
{
    /**
     * Relative path to the properties file.
     */
    private static final String PROPERTIES = "resources/client.properties";

    /**
     * Bitbucket API client.
     */
    private final BitbucketClient bitbucketClient;

    /**
     * Initializes the object.
     *
     * @param bitbucketClient a Bitbucket API client
     */
    public LoginCommand(final BitbucketClient bitbucketClient)
    {
        this.bitbucketClient = bitbucketClient;

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

        restoreTokens();
        bitbucketClient.addTokenRefreshListener(this);
    }

    /**
     * Restores tokens.
     */
    protected void restoreTokens()
    {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        bitbucketClient.setRefreshToken(prefs.get("refreshToken", null));
        bitbucketClient.setAccessToken(prefs.get("accessToken", null));

        Instant expiry = Instant.parse(
            prefs.get("accessTokenExpiry", Instant.now().toString()));
        bitbucketClient.setAccessTokenExpiry(expiry);
    }

    /**
     * Saves tokens for later invocations.
     */
    protected void saveTokens()
    {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        prefs.put("refreshToken", bitbucketClient.getRefreshToken());
        prefs.put("accessToken", bitbucketClient.getAccessToken());

        Instant expiry = bitbucketClient.getAccessTokenExpiry();
        prefs.put("accessTokenExpiry", expiry.toString());

        try {
            prefs.flush();
        }
        catch (final BackingStoreException exception) {
            // @todo What else can we do?
            System.err.format("Failed to save tokens: %s\n", exception);
        }
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

        try {
            bitbucketClient.login(username, password);
        }
        catch (final ClientErrorException exception) {
            throw new CLIException("Login failed", exception);
        }
        saveTokens();
    }

    @Override
    public void tokenRefreshed(final TokenRefreshEvent event)
    {
        saveTokens();
    }
}
