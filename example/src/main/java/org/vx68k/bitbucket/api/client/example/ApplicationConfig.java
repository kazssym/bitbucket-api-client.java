/*
 * ApplicationConfig
 * Copyright (C) 2015 Nishimura Software Studio
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.vx68k.bitbucket.api.client.example;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Credentials;

/**
 * Application configuration.
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
@Named("config")
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 2L;

    private static final String BITBUCKET_OAUTH_CLIENT_ID_ENV =
            "BITBUCKET_OAUTH_CLIENT_ID";

    private static final String BITBUCKET_OAUTH_CLIENT_SECRET_ENV =
            "BITBUCKET_OAUTH_CLIENT_SECRET";

    private Client bitbucketClient;

    /**
     * Constructs this object without initialization.
     */
    public ApplicationConfig() {
    }

    /**
     * Returns the Bitbucket client with the configured client credentials.
     * This method shall returns the same Bitbucket client.
     * @return Bitbucket client
     * @since 4.0
     */
    public Client getBitbucketClient() {
        synchronized (this) {
            if (bitbucketClient == null) {
                bitbucketClient = getBitbucketClient(getClientCredentials());
            }
        }
        return bitbucketClient;
    }

    /**
     * Returns a Bitbucket client with client credentials.
     * @param clientCredentials client credentials
     * @return Bitbucket client
     * @since 4.0
     */
    public static Client getBitbucketClient(Credentials clientCredentials) {
        return new Client(clientCredentials);
    }

    /**
     * Returns the configured client credentials.
     * @return configured client credentials
     * @since 4.0
     */
    protected static Credentials getClientCredentials() {
        String clientId = System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_ID,
                System.getenv(BITBUCKET_OAUTH_CLIENT_ID_ENV));
        String clientSecret = System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_SECRET,
                System.getenv(BITBUCKET_OAUTH_CLIENT_SECRET_ENV));
        if (clientId == null || clientSecret == null) {
            return null;
        }
        return new Credentials(clientId, clientSecret);
    }

    /**
     * Returns the tracking ID for Google Analytics.
     * @return tracking ID
     * @since 3.0
     */
    public String getAnalyticsId() {
        return System.getProperty(Properties.GOOGLE_ANALYTICS_TRACKING_ID);
    }
}
