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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownServiceException;
import java.util.Date;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
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

    private static final long serialVersionUID = 2L;

    /**
     * Bitbucket OAuth authorization request endpoint URI as a string.
     */
    private static final String AUTHORIZATION_ENDPOINT
            = "https://bitbucket.org/site/oauth2/authorize";

    /**
     * Bitbucket OAuth token request endpoint URI as a string.
     */
    private static final String TOKEN_ENDPOINT
            = "https://bitbucket.org/site/oauth2/access_token";

    private static final String BEARER_TOKEN_TYPE = "bearer";

    private static final HttpTransport transport = new NetHttpTransport();

    private Credentials credentials;

    private URI redirectionEndpoint;

    /**
     * Constructs a Bitbucket API client with no credentials.
     */
    public Client() {
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
     * @return OAuth client credentials, or <code>null</code> if this object
     * has no credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Returns the redirection endpoint URI of this object.
     * @return redirection endpoint URI, or <code>null</code> if no redirection
     * endpoint is specified
     * @since 2.0
     */
    public URI getRedirectionEndpoint() {
        return redirectionEndpoint;
    }

    /**
     * Sets the OAuth client credentials of this object.
     * @param credentials OAuth client credentials; if this value is
     * <code>null</code>, this object shall not have any credentials
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Sets the redirection endpoint URI of this object.
     * @param redirectionEndpoint redirection endpoint URI; if this argument is
     * <code>null</code>, no redirection endpoint shall be specified
     */
    public void setRedirectionEndpoint(URI redirectionEndpoint) {
        this.redirectionEndpoint = redirectionEndpoint;
    }

    /**
     * Returns the authorization endpoint URI for this object.
     * @param state state string for the authorization request; if this
     * argument is <code>null</code>, the state parameter shall not be used
     * @return authorization endpoint URI, or <code>null</code> if this object
     * has no OAuth client credentials
     * @throws URISyntaxException if the authoriztion endpoint could not be
     * parsed as a URI
     * @throws NullPointerException if this object has no OAuth client
     * credentials
     */
    public URI getAuthorizationEndpoint(String state)
            throws URISyntaxException {
        AuthorizationCodeFlow flow = getAuthorizationCodeFlow(false);
        AuthorizationCodeRequestUrl request = flow.newAuthorizationUrl();
        if (redirectionEndpoint != null) {
            request.setRedirectUri(redirectionEndpoint.toString());
        }
        if (state != null) {
            request.setState(state);
        }
        return new URI(request.build());
    }

    /**
     * Returns an anonymous Bitbucket API service.
     * @return anonymous Bitbucket API service
     */
    public Service getService() {
        return new RestService();
    }

    /**
     * Returns an authorized Bitbucket API service.
     * @param authorizationCode authorization code received by the redirection
     * endpoint
     * @return authorized Bitbucket API service
     * @throws IOException if an I/O exception occurs
     * @throws NullPointerException if this object has no OAuth client
     * credentials
     */
    public Service getService(String authorizationCode) throws IOException {
        AuthorizationCodeFlow flow = getAuthorizationCodeFlow(true);
        AuthorizationCodeTokenRequest request
                = flow.newTokenRequest(authorizationCode);
        if (redirectionEndpoint != null) {
            request.setRedirectUri(redirectionEndpoint.toString());
        }

        TokenResponse tokenResponse = request.execute();
        String tokenType = tokenResponse.getTokenType();
        if (!tokenType.equals(BEARER_TOKEN_TYPE)) {
            throw new UnknownServiceException("Unsupported token type");
        }
        return new RestService(tokenResponse);
    }

    /**
     * Returns a {@link AuthorizationCodeFlow} object for OAuth requests.
     * <em>As of version 2.0, this method has been changed to protected and
     * does no longer return <code>null</code> if this object has no OAuth
     * client credentials.</em>
     * For authorization requests, use {@link #getAuthorizationEndpoint}
     * instead.
     * @param forTokenRequest <code>true</code> for token requests, or
     * <code>false</code> for authorization requests
     * @return {@link AuthorizationCodeFlow} object
     * @throws NullPointerException if this object has no OAuth client
     * credentials
     */
    protected AuthorizationCodeFlow getAuthorizationCodeFlow(
            boolean forTokenRequest) {
        if (credentials == null) {
            throw new NullPointerException("No OAuth client credentials");
        }

        HttpExecuteInterceptor clientAuthentication;
        if (forTokenRequest) {
            clientAuthentication = getBasicAuthentication();
        } else {
            clientAuthentication = getClientParameters();
        }

        return new AuthorizationCodeFlow(
                BearerToken.authorizationHeaderAccessMethod(), transport,
                JacksonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_ENDPOINT), clientAuthentication,
                credentials.getId(), AUTHORIZATION_ENDPOINT);
    }

    /**
     * Returns a {@link ClientParametersAuthentication} object only with the
     * client identifier.
     *
     * @return new {@link ClientParametersAuthentication} object
     */
    protected ClientParametersAuthentication getClientParameters() {
        // Sets only the client identifier that is required in authorization
        // requests.
        return new ClientParametersAuthentication(credentials.getId(), null);
    }

    /**
     * Returns a {@link BasicAuthentication} object with the client identifier
     * and its shared secret.
     *
     * @return {@link BasicAuthentication} object
     */
    protected BasicAuthentication getBasicAuthentication() {
        return new BasicAuthentication(
                credentials.getId(), credentials.getSecret());
    }

    /**
     * Bitbucket REST API service.
     * @author Kaz Nishimura
     * @since 2.0
     */
    protected class RestService extends Service {

        /**
         * Access token.
         */
        private String accessToken;

        /**
         * Expiration time of the access token.
         */
        private Date expiration;

        /**
         * Refresh token, or <code>null</code> if not specified.
         */
        private String refreshToken;

        /**
         * Constructs this object with no authentication.
         */
        public RestService() {
        }

        /**
         * Constructs this object from a token response.
         * @param tokenResponse token response
         */
        public RestService(TokenResponse tokenResponse) {
            accessToken = tokenResponse.getAccessToken();

            Long expiresIn = tokenResponse.getExpiresInSeconds();
            if (expiresIn != null) {
                Date now = new Date();
                expiration = new Date(now.getTime() + expiresIn * 1000L);
            }
            refreshToken = tokenResponse.getRefreshToken();
        }

        @Override
        public boolean isAuthenticated() {
            if (accessToken == null) {
                return false;
            }
            if (expiration != null && expiration.before(new Date())) {
                // TODO: Refresh the access token.
                return false;
            }
            return true;
        }
    }
}
