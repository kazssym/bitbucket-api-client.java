/*
 * OAuthCient
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

package org.vx68k.bitbucket.api.client.oauth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownServiceException;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Service;

/**
 * Bitbucket client that supports OAuth authorization.
 * @author Kaz Nishimura
 * @since 4.0
 */
public class OAuthClient extends Client {

    private static final long serialVersionUID = 4L;

    private OAuthCredentials credentials;

    /**
     * OAuth authorization request endpoint of Bitbucket.
     */
    private static final String AUTHORIZATION_ENDPOINT =
            "https://bitbucket.org/site/oauth2/authorize";

    /**
     * OAuth token request endpoint of Bitbucket.
     */
    private static final String TOKEN_ENDPOINT =
            "https://bitbucket.org/site/oauth2/access_token";

    private static final String BEARER_TOKEN_TYPE = "bearer";

    /**
     * Constructs this object with no property values.
     */
    public OAuthClient() {
    }

    /**
     * Constructs this object with a client identifier and a client secret.
     * This constructor is equivalent to {@link #OAuthClient(OAuthCredentials)}
     * called with a newly created {@link OAuthCredentials} object.
     * @param clientId client identifier
     * @param clientSecret client secret
     */
    public OAuthClient(String clientId, String clientSecret) {
        this(new OAuthCredentials(clientId, clientSecret));
    }

    /**
     * Constructs this object with client credntials.
     * This constructor is equivalent to the default one followed by a call to
     * {@link #setCredentials}.
     * @param credentials client credentials
     */
    public OAuthClient(OAuthCredentials credentials) {
        setCredentials(credentials);
    }

    /**
     * Returns the OAuth client credentials of this object.
     * @return OAuth client credentials, or <code>null</code> if this object
     * has no credentials
     */
    public OAuthCredentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the OAuth client credentials of this object.
     * @param credentials OAuth client credentials; if this value is
     * <code>null</code>, this object shall not have any credentials
     */
    public void setCredentials(OAuthCredentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Returns the authorization endpoint URI for this object.
     * @param redirectionEndpoint redirection endpoint URI for the
     * authorization request; if this argument is <code>null</code>, the
     * <code>redirect_uri</code> parameter shall not be used.
     * @param state opaque state string for the authorization request; if this
     * argument is <code>null</code>, the <code>state</code> parameter shall
     * not be used
     * @return authorization endpoint URI
     * @throws URISyntaxException if the authorization endpoint URI could not
     * be constructed
     * @throws NullPointerException if this object has no client credentials
     */
    public URI getAuthorizationEndpoint(URI redirectionEndpoint, String state)
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
     * Returns an authorized Bitbucket API service.
     * @param authorizationCode authorization code received by the redirection
     * endpoint
     * @param redirectionEndpoint redirection endpoint URI that was used in the
     * authorization request; if this argument is <code>null</code>, the
     * <code>redirect_uri</code> parameter shall not be used.
     * @return authorized Bitbucket REST API service
     * @throws IOException if an I/O exception has occurred
     * @throws NullPointerException if this object has no client credentials
     */
    public Service getService(
            String authorizationCode, URI redirectionEndpoint)
            throws IOException {
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
        return new RestService(
                flow.createAndStoreCredential(tokenResponse, getUser()));
    }

    /**
     * Returns a authorization code flow for OAuth requests.
     * @param forTokenRequest <code>true</code> for token requests, or
     * <code>false</code> for authorization requests
     * @return {@link AuthorizationCodeFlow} object
     * @throws NullPointerException if this object has no client credentials
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
                BearerToken.authorizationHeaderAccessMethod(),
                getHttpTransport(), JacksonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_ENDPOINT), clientAuthentication,
                credentials.getId(), AUTHORIZATION_ENDPOINT);
    }

    /**
     * Returns a HTTP execute interceptor with only the client identifier as a
     * request parameter.
     * @return HTTP execute interceptor
     */
    protected HttpExecuteInterceptor getClientParameters() {
        // Sets only the client identifier that is required in authorization
        // requests.
        return new ClientParametersAuthentication(credentials.getId(), null);
    }

    /**
     * Returns a HTTP execute interceptor with the client identifier and the
     * client secret for Basic authentication.
     * @return HTTP execute interceptor
     */
    protected HttpExecuteInterceptor getBasicAuthentication() {
        return new BasicAuthentication(
                credentials.getId(), credentials.getSecret());
    }
}
