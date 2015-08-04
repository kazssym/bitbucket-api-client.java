/*
 * BitbucketClient
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

package org.vx68k.bitbucket.api.client;

import java.io.Serializable;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Bitbucket API client.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String AUTHORIZATION_ENDPOINT_URL
            = "https://bitbucket.org/site/oauth2/authorize";
    private static final String TOKEN_ENDPOINT_URL
            = "https://bitbucket.org/site/oauth2/access_token";

    private final Credentials credentials;

    /**
     * Constructs a Bitbucket API client with an empty credentials.
     */
    public BitbucketClient() {
        this(new Credentials());
    }

    /**
     * Constructs a Bitbucket API client with a client identifier and a client
     * shared secret.
     *
     * @param ID client identifier
     * @param secret client shared secret
     */
    public BitbucketClient(String ID, String secret) {
        this(new Credentials(ID, secret));
    }

    /**
     * Constructs a Bitbucket API client with OAuth client credentials.
     *
     * @param credentials OAuth client credentials
     */
    public BitbucketClient(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Returns the OAuth client credentials of this object.
     *
     * @return OAuth client credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Returns a {@link ClientParametersAuthentication} object only with the
     * client identifier.
     *
     * @return new {@link ClientParametersAuthentication} object
     */
    protected ClientParametersAuthentication getClientParameters() {
        if (credentials.isEmpty()) {
            return null;
        }
        // Sets only the client identifier that is required in authorization
        // requests.
        return new ClientParametersAuthentication(credentials.getID(), null);
    }

    /**
     * Returns a {@link BasicAuthentication} object with the client identifier
     * and its shared secret.
     *
     * @return {@link BasicAuthentication} object
     */
    protected BasicAuthentication getBasicAuthentication() {
        if (credentials.isEmpty()) {
            return null;
        }
        return new BasicAuthentication(
                credentials.getID(), credentials.getSecret());
    }

    public AuthorizationCodeFlow getAuthorizationCodeFlow(
            boolean forTokenRequest) {
        if (credentials.isEmpty()) {
            // API access will be anonymous.
            return null;
        }

        HttpExecuteInterceptor clientAuthentication = getClientParameters();
        if (forTokenRequest) {
            clientAuthentication = getBasicAuthentication();
        }

        return new AuthorizationCodeFlow(
                BearerToken.authorizationHeaderAccessMethod(),
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_ENDPOINT_URL), clientAuthentication,
                credentials.getID(), AUTHORIZATION_ENDPOINT_URL);
    }

    /**
     * Returns a Bitbucket API session using the client credentials.
     *
     * @return Bitbucket API session
     */
    public Session getSession() {
        return new Session(credentials);
    }
}
