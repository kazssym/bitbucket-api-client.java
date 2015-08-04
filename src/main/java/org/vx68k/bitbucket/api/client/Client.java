/*
 * Client
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

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownServiceException;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Bitbucket API client.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String AUTHORIZATION_ENDPOINT =
            "https://bitbucket.org/site/oauth2/authorize";
    private static final String TOKEN_ENDPOINT =
            "https://bitbucket.org/site/oauth2/access_token";

    private static final String BEARER_TOKEN_TYPE = "bearer";

    private transient final HttpTransport transport = new NetHttpTransport();

    private final Credentials credentials;

    /**
     * Constructs a Bitbucket API client with an empty credentials.
     */
    public Client() {
        this(new Credentials());
    }

    /**
     * Constructs a Bitbucket API client with a client identifier and a client
     * shared secret.
     *
     * @param ID client identifier
     * @param secret client shared secret
     */
    public Client(String ID, String secret) {
        this(new Credentials(ID, secret));
    }

    /**
     * Constructs a Bitbucket API client with OAuth client credentials.
     *
     * @param credentials OAuth client credentials
     */
    public Client(Credentials credentials) {
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
                BearerToken.authorizationHeaderAccessMethod(), transport,
                JacksonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_ENDPOINT), clientAuthentication,
                credentials.getID(), AUTHORIZATION_ENDPOINT);
    }

    /**
     * Returns an anonymous Bitbucket API service.
     *
     * @return anonymous Bitbucket API service
     * @exception IOException if an I/O exception occurs
     */
    public Service getService() throws IOException {
        return getService(null);
    }

    /**
     * Returns a Bitbucket API service.
     *
     * @param authorizationCode authorization code received by the redirection
     * endpoint, or <code>null</code> if API access shall be anonymous
     * @return Bitbucket API service
     * @exception IOException if an I/O exception occurs
     */
    public Service getService(String authorizationCode) throws IOException {
        TokenResponse tokenResponse = null;
        if (authorizationCode != null) {
            AuthorizationCodeFlow flow = getAuthorizationCodeFlow(true);
            if (flow == null) {
                throw new IllegalStateException("No client credentials");
            }

            AuthorizationCodeTokenRequest tokenRequest
                    = flow.newTokenRequest(authorizationCode);
            tokenResponse = tokenRequest.execute();
            String tokenType = tokenResponse.getTokenType();
            if (!tokenType.equals(BEARER_TOKEN_TYPE)) {
                throw new UnknownServiceException("Unsupported token type");
            }
        }
        return new Service(tokenResponse);
    }
}
