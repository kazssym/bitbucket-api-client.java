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

    private static final long serialVersionUID = 5L;

    /**
     * OAuth authorization request endpoint of Bitbucket.
     */
    private static final String AUTHORIZATION_ENDPOINT_URI =
            "https://bitbucket.org/site/oauth2/authorize";

    /**
     * OAuth token request endpoint of Bitbucket.
     */
    private static final String TOKEN_ENDPOINT_URI =
            "https://bitbucket.org/site/oauth2/access_token";

    private static final String BEARER_TOKEN_TYPE = "bearer";

    private String clientId;
    private String clientSecret;

    private transient String redirectionEndpointUri;

    /**
     * Constructs this object with no property values.
     */
    public OAuthClient() {
    }

    /**
     * Constructs this object with a client identifier and a client secret.
     * This constructor is equivalent to the default one followed by each one
     * call of {@link #setClientId} and {@link #setClientSecret}.
     * @param clientId client identifier
     * @param clientSecret client secret
     */
    public OAuthClient(final String clientId, final String clientSecret) {
        setClientId(clientId);
        setClientSecret(clientSecret);
    }

    /**
     * Constructs this object with client credntials.
     * This constructor is equivalent to the default one followed by a call of
     * {@link #setCredentials}.
     * @param credentials client credentials
     * @deprecated As of version 5.0, replaced by
     * {@link #OAuthClient(String, String)}
     */
    @Deprecated
    public OAuthClient(final OAuthCredentials credentials) {
        setCredentials(credentials);
    }

    /**
     * Returns the client identifier.
     * @return client identifier
     * @since 5.0
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the client secret.
     * @return client secret
     * @since 5.0
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets the client identifier.
     * @param value client identifier to be set; if this value is an empty
     * string, the client identifier shall be set to <code>null</code>
     * @since 5.0
     */
    public void setClientId(final String value) {
        if (value != null && value.isEmpty()) {
            clientId = null;
        } else {
            clientId = value;
        }
    }

    /**
     * Sets the client secret.
     * @param value client secret to be set; if this value is an empty
     * string, the client secret shall be set to <code>null</code>
     * @since 5.0
     */
    public void setClientSecret(final String value) {
        if (value != null && value.isEmpty()) {
            clientSecret = null;
        } else {
            clientSecret = value;
        }
    }

    /**
     * Tests if this instance has client credentials.
     * @return <code>true</code> if this instance has client credentials;
     * <code>false</code> otherwise
     * @since 5.0
     */
    public boolean isConfiguredForOAuth() {
        return !(getClientId() == null || getClientSecret() == null);
    }

    /**
     * Returns the OAuth client credentials of this object.
     * @return OAuth client credentials, or <code>null</code> if this object
     * has no credentials
     * @deprecated As of version 5.0, replaced by {@link #getClientId} and
     * {@link #getClientSecret}
     */
    @Deprecated
    public OAuthCredentials getCredentials() {
        if (isConfiguredForOAuth()) {
            return new OAuthCredentials(getClientId(), getClientSecret());
        }
        return null;
    }

    /**
     * Sets the OAuth client credentials of this object.
     * @param credentials OAuth client credentials; if this value is
     * <code>null</code>, this object shall not have any credentials
     * @deprecated As of version 5.0, replaced by {@link #setClientId} and
     * {@link #setClientSecret}
     */
    @Deprecated
    public void setCredentials(final OAuthCredentials credentials) {
        if (credentials != null) {
            setClientId(credentials.getId());
            setClientSecret(credentials.getSecret());
        } else {
            setClientId(null);
            setClientSecret(null);
        }
    }

    /**
     * Generates the authorization request URI for the client credentials
     * without the <code>redirect_uri</code> and <code>state</code> parameters.
     * @return authorization request URI
     * @throws NullPointerException if this object had no client credentials
     * @since 5.0
     */
    public String authorizationRequestUri() {
        return authorizationRequestUri(null, null);
    }

    /**
     * Generates the authorization request URI for the client credentials
     * without the <code>state</code> parameter.
     * @param redirectionEndpointUri redirection endpoint for the authorization
     * and token requests; if this value is <code>null</code>,
     * the <code>redirect_uri</code> parameter shall not be included.
     * @return authorization request URI
     * @throws NullPointerException if this object had no client credentials
     * @since 5.0
     */
    public String authorizationRequestUri(final URI redirectionEndpointUri) {
        return authorizationRequestUri(redirectionEndpointUri, null);
    }

    /**
     * Generates the authorization request URI for the client credentials.
     * @param redirectionEndpoint redirection endpoint for the authorization
     * and token requests; if this value is <code>null</code>, the
     * <code>redirect_uri</code> parameter shall not be included.
     * @param state opaque state string for the authorization request; if this
     * argument is <code>null</code>, the <code>state</code> parameter shall
     * not be used
     * @return authorization request URI
     * @throws NullPointerException if this object had no client credentials
     * @since 5.0
     */
    public String authorizationRequestUri(
            final URI redirectionEndpoint, final String state) {
        redirectionEndpointUri = null;
        if (redirectionEndpoint != null) {
            redirectionEndpointUri = redirectionEndpoint.toString();
        }

        AuthorizationCodeFlow flow = getAuthorizationCodeFlow(false);
        AuthorizationCodeRequestUrl request = flow.newAuthorizationUrl();
        if (redirectionEndpointUri != null) {
            request.setRedirectUri(redirectionEndpointUri);
        }
        if (state != null) {
            request.setState(state);
        }
        return request.build();
    }

    /**
     * Returns an authorized Bitbucket API service.
     * @param authorizationCode authorization code received by the redirection
     * endpoint
     * @return authorized Bitbucket API service
     * @throws IOException if an I/O exception has occurred
     * @throws NullPointerException if this object has no client credentials
     * @since 5.0
     */
    public Service getService(final String authorizationCode)
            throws IOException {
        AuthorizationCodeFlow flow = getAuthorizationCodeFlow(true);
        AuthorizationCodeTokenRequest request
                = flow.newTokenRequest(authorizationCode);
        if (redirectionEndpointUri != null) {
            request.setRedirectUri(redirectionEndpointUri);
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
            final boolean forTokenRequest) {
        if (!isConfiguredForOAuth()) {
            throw new NullPointerException("No OAuth client credentials");
        }

        HttpExecuteInterceptor clientAuthenticator;
        if (forTokenRequest) {
            clientAuthenticator = getBasicAuthentication();
        } else {
            clientAuthenticator = getClientParameters();
        }
        return new AuthorizationCodeFlow(
                BearerToken.authorizationHeaderAccessMethod(),
                getHttpTransport(), JacksonFactory.getDefaultInstance(),
                new GenericUrl(TOKEN_ENDPOINT_URI), clientAuthenticator,
                getClientId(), AUTHORIZATION_ENDPOINT_URI);
    }

    /**
     * Returns a HTTP execute interceptor with only the client identifier as a
     * request parameter.
     * @return HTTP execute interceptor
     */
    protected HttpExecuteInterceptor getClientParameters() {
        // Sets only the client identifier that is required in authorization
        // requests.
        return new ClientParametersAuthentication(getClientId(), null);
    }

    /**
     * Returns a HTTP execute interceptor with the client identifier and the
     * client secret for Basic authentication.
     * @return HTTP execute interceptor
     */
    protected HttpExecuteInterceptor getBasicAuthentication() {
        return new BasicAuthentication(getClientId(), getClientSecret());
    }
}
