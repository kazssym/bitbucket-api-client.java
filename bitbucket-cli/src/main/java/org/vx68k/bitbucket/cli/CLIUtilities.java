/*
 * CLIUtilities.java
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

import java.time.Instant;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.vx68k.bitbucket.client.BitbucketClient;
import org.vx68k.bitbucket.client.util.OAuth2Authenticator;

/**
 * Utilities for the CLI.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public final class CLIUtilities
{
    /**
     * Does nothing but denies direct instantiations.
     */
    protected CLIUtilities()
    {
    }

    /**
     * Saves the token information to a persistent store.
     *
     * @param bitbucketClient a Bitbucket API client
     */
    public static void saveTokens(final BitbucketClient bitbucketClient)
    {
        OAuth2Authenticator oAuth2 = bitbucketClient.getOAuth2Authenticator();

        Preferences preferences = Preferences.userNodeForPackage(CLI.class);
        preferences.put("refreshToken", oAuth2.getRefreshToken());
        preferences.put("accessToken", oAuth2.getAccessToken());

        Instant expiryInstant = oAuth2.getExpiration();
        preferences.put("accessTokenExpiry", expiryInstant.toString());

        try {
            preferences.flush();
        }
        catch (final BackingStoreException exception) {
            // @todo What else can we do?
            System.err.format("Failed to save tokens: %s\n", exception);
        }
    }

    /**
     * Restores the token information from a persistent store.
     *
     * @param bitbucketClient a Bitbucket API client
     */
    public static void restoreTokens(final BitbucketClient bitbucketClient)
    {
        OAuth2Authenticator oAuth2 = bitbucketClient.getOAuth2Authenticator();

        Preferences preferences = Preferences.userNodeForPackage(CLI.class);
        oAuth2.setRefreshToken(preferences.get("refreshToken", null));
        oAuth2.setAccessToken(preferences.get("accessToken", null));

        String expiry = preferences.get("accessTokenExpiry", null);
        if (expiry != null) {
            oAuth2.setExpiration(Instant.parse(expiry));
        }
        else {
            oAuth2.setExpiration(null);
        }
    }
}
