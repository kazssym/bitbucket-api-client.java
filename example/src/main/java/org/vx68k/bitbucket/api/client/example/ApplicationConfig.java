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
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Credentials;

/**
 * Application configuration.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
@Named("config")
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Produces
    public static Client getBitbucketClient() {
        String clientId = System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_ID,
                System.getenv("BITBUCKET_CLIENT_ID"));
        String clientSecret = System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_SECRET,
                System.getenv("BITBUCKET_CLIENT_SECRET"));

        Client client = new Client();
        if (clientId != null && clientSecret != null) {
            client.setCredentials(new Credentials(clientId, clientSecret));
        }
        return client;
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
