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
import org.vx68k.bitbucket.api.client.oauth.OAuthCredentials;
import org.vx68k.bitbucket.api.client.oauth.OAuthClient;

/**
 * Application configuration.
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
@Named("config")
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 4L;

    private static final String BITBUCKET_OAUTH_CLIENT_ID_ENV =
            "BITBUCKET_OAUTH_CLIENT_ID";

    private static final String BITBUCKET_OAUTH_CLIENT_SECRET_ENV =
            "BITBUCKET_OAUTH_CLIENT_SECRET";

    private OAuthClient bitbucketClient;

    /**
     * Returns the Bitbucket client with the configured client credentials.
     * This method shall returns the same Bitbucket client.
     * @return Bitbucket client
     * @since 4.0
     */
    public OAuthClient getBitbucketClient() {
        synchronized (this) {
            if (bitbucketClient == null) {
                bitbucketClient = getBitbucketClient(
                        getClientId(), getClientSecret());
            }
        }
        return bitbucketClient;
    }

    /**
     * Returns a Bitbucket API client with a client identifier and a client
     * secret.
     * @param clientId client identifier
     * @return Bitbucket API client
     * @since 5.0
     */
    public static OAuthClient getBitbucketClient(
            String clientId, String clientSecret) {
        return new OAuthClient(clientId, clientSecret);
    }

    /**
     * Returns a Bitbucket API client with client credentials.
     * @param clientCredentials client credentials
     * @return Bitbucket API client
     * @since 4.0
     * @deprecated As of version 5.0, replaced by
     * {@link #getBitbucketClient(String, String)}
     */
    @Deprecated
    public static OAuthClient getBitbucketClient(
            OAuthCredentials clientCredentials) {
        return getBitbucketClient(
                clientCredentials.getId(), clientCredentials.getSecret());
    }

    /**
     * Returns the configured client identifier.
     * @return configured client identifier
     * @since 5.0
     */
    protected static String getClientId() {
        return System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_ID,
                System.getenv(BITBUCKET_OAUTH_CLIENT_ID_ENV));
    }

    /**
     * Returns the configured client secret.
     * @return configured client secret
     * @since 5.0
     */
    protected static String getClientSecret() {
        return System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_SECRET,
                System.getenv(BITBUCKET_OAUTH_CLIENT_SECRET_ENV));
    }

    /**
     * Returns the configured client credentials.
     * @return configured client credentials
     * @since 4.0
     * @deprecated As of version 5.0, replaced by {@link #getClientId} and
     * {@link #getClientSecret}
     */
    @Deprecated
    protected static OAuthCredentials getClientCredentials() {
        String clientId = getClientId();
        String clientSecret = getClientSecret();
        if (clientId == null || clientSecret == null) {
            return null;
        }
        return new OAuthCredentials(clientId, clientSecret);
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
