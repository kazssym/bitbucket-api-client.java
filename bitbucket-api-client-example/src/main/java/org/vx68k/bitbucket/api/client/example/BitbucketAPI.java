/*
 * BitbucketAPI.java - class BitbucketAPI
 * Copyright (C) 2015-2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client.example;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.vx68k.bitbucket.api.client.oauth.OAuthCredentials;
import org.vx68k.bitbucket.api.client.oauth.OAuthClient;

/**
 * Bitbucket API.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@ApplicationScoped
@Named("bitbucket")
public class BitbucketAPI implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Prefix for the system property keys.
     */
    private static final String PROPERTY_PREFIX =
        "org.vx68k.bitbucket.api.client.example";

    /**
     * Property key for the Bitbucket OAuth client identifier (consumer key).
     */
    public static final String OAUTH_CLIENT_ID_PROPERTY =
        PROPERTY_PREFIX + ".oauth.clientId";

    /**
     * Property key for the Bitbucket OAuth client secret (consumer secret).
     */
    public static final String OAUTH_CLIENT_SECRET_PROPERTY =
        PROPERTY_PREFIX + ".oauth.clientSecret";

    /**
     * Property key for the Google Analytics tracking ID.
     */
    public static final String GOOGLE_ANALYTICS_ID_PROPERTY =
        PROPERTY_PREFIX + ".analyticsId";

    private OAuthClient bitbucketClient;

    /**
     * Returns the Bitbucket client with the configured client credentials.
     * This method shall returns the same Bitbucket client.
     *
     * @return Bitbucket client
     */
    public OAuthClient getBitbucketClient()
    {
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
     *
     * @param clientId client identifier
     * @return Bitbucket API client
     */
    public static OAuthClient getBitbucketClient(
            final String clientId, final String clientSecret)
    {
        return new OAuthClient(clientId, clientSecret);
    }

    /**
     * Returns a Bitbucket API client with client credentials.
     *
     * @param clientCredentials client credentials
     * @return Bitbucket API client
     * @deprecated As of version 5.0, replaced by
     * {@link #getBitbucketClient(String, String)}
     */
    @Deprecated
    public static OAuthClient getBitbucketClient(
            final OAuthCredentials clientCredentials)
    {
        return getBitbucketClient(
                clientCredentials.getId(), clientCredentials.getSecret());
    }

    /**
     * Returns the configured client identifier.
     * @return configured client identifier
     */
    protected static String getClientId()
    {
        return System.getProperty(OAUTH_CLIENT_ID_PROPERTY);
    }

    /**
     * Returns the configured client secret.
     * @return configured client secret
     */
    protected static String getClientSecret()
    {
        return System.getProperty(OAUTH_CLIENT_SECRET_PROPERTY);
    }

    /**
     * Returns the configured client credentials.
     *
     * @return configured client credentials
     * @deprecated As of version 5.0, replaced by {@link #getClientId} and
     * {@link #getClientSecret}
     */
    @Deprecated
    protected static OAuthCredentials getClientCredentials()
    {
        String clientId = getClientId();
        String clientSecret = getClientSecret();
        if (clientId == null || clientSecret == null) {
            return null;
        }
        return new OAuthCredentials(clientId, clientSecret);
    }

    /**
     * Returns the tracking ID for Google Analytics.
     *
     * @return the tracking ID
     */
    public String getAnalyticsId()
    {
        return System.getProperty(GOOGLE_ANALYTICS_ID_PROPERTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int code = getClass().hashCode();
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object)
    {
        if (this != object) {
            if (object == null || object.getClass() != getClass()) {
                return false;
            }
        }
        return true;
    }
}
