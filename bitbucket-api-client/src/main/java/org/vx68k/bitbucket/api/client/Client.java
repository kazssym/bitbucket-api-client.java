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
import javax.json.Json;
import javax.json.JsonReader;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.vx68k.bitbucket.api.BitbucketUser;

/**
 * Bitbucket REST API client.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Client implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * Undocumented.
     */
    private static final HttpTransport httpTransport = new NetHttpTransport();

    /**
     * Undocumented.
     */
    private String user;

    /**
     * Undocumented.
     */
    private String password;

    /**
     * Constructs this object with no property values.
     */
    public Client() {
    }

    /**
     * Constructs this object with a user name and a password for Basic
     * authentication.
     * This constructor is equivalent to the default one followed by calls to
     * {@link #setUser} and {@link #setPassword}.
     * @param user user name
     * @param password password
     * @since 4.0
     */
    public Client(final String user, final String password) {
        setUser(user);
        setPassword(password);
    }

    /**
     * Returns the HTTP transport.
     * @return HTTP transport
     * @since 4.0
     */
    protected static HttpTransport getHttpTransport() {
        return httpTransport;
    }

    /**
     * Returns the user name for Basic authentication.
     * @return user name
     * @since 4.0
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns the password for Basic authentication.
     * @return password
     * @since 4.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user name for Basic authentication.
     * @param value user name to be set
     * @since 4.0
     */
    public void setUser(final String value) {
        user = value;
    }

    /**
     * Sets the password for Basic authentication.
     * @param value password to be set
     * @since 4.0
     */
    public void setPassword(final String value) {
        password = value;
    }

    /**
     * Returns a Bitbucket REST API service.
     * If both the user name and the password is not <code>null</code>, they
     * are used for Basic authentication.
     * @return Bitbucket REST API service
     */
    public Service getService() {
        if (user != null && password != null) {
            return getService(user, password);
        }
        return new RestService(null);
    }

    /**
     * Returns a Bitbucket REST API service with a user name and a password for
     * Basic authentication.
     * @param user user name
     * @param password password
     * @return Bitbucket REST API service
     * @throws NullPointerException if either the user name or the password is
     * <code>null</code>
     * @since 4.0
     */
    public Service getService(final String user, final String password) {
        return new RestService(new BasicAuthentication(user, password));
    }

    /**
     * Bitbucket REST API service.
     * @author Kaz Nishimura
     * @since 2.0
     */
    protected class RestService extends Service {

        /**
         * Undocumented.
         */
        private static final String API_ROOT = "https://api.bitbucket.org/";

        /**
         * Undocumented.
         */
        private static final String USER_ENDPOINT_PATH = "/2.0/user";

        /**
         * Undocumented.
         */
        private static final String USERS_ENDPOINT_PATH = "/2.0/users";

        /**
         * Undocumented.
         */
        private final HttpRequestFactory requestFactory =
                getHttpTransport().createRequestFactory();

        /**
         * HTTP request interceptor for authentication.
         */
        private final HttpExecuteInterceptor authentication;

        /**
         * Cached current user of the Bitbucket REST API.
         * @since 3.0
         */
        private BitbucketUser currentUser;

        /**
         * Constructs this object with a HTTP execute interceptor for
         * authentication.
         * @param value HTTP execute interceptor
         */
        public RestService(final HttpExecuteInterceptor value) {
            authentication = value;
        }

        /**
         * Returns the URI of an endpoint path.
         * @param path endpoint path
         * @return endpoint URI
         */
        protected URI getEndpoint(final String path) {
            URI root = URI.create(API_ROOT);
            return root.resolve(URI.create(path));
        }

        /**
         * Clears the cached current Bitbucket user.
         * @since 3.0
         */
        public void clearCurrentUser() {
            synchronized (this) {
                currentUser = null;
            }
        }

        /**
         * Undocumented.
         *
         * @param response undocumented
         * @return undocumented
         * @exception IOException undocumented
         */
        protected BitbucketUser getUser(final HttpResponse response)
                throws IOException {
            JsonReader reader = Json.createReader(response.getContent());
            return new BitbucketClientUser(reader.readObject());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isAuthenticated() {
            try {
                return getCurrentUser() != null;
            } catch (IOException exception) {
                return false;
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BitbucketUser getCurrentUser() throws IOException {
            if (authentication == null) {
                return null;
            }

            synchronized (this) {
                if (currentUser == null) {
                    URI endpoint = getEndpoint(USER_ENDPOINT_PATH);
                    HttpRequest request = requestFactory.buildGetRequest(
                            new GenericUrl(endpoint.toString()));
                    request.setInterceptor(authentication);
                    currentUser = getUser(request.execute());
                }
            }
            return currentUser;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BitbucketUser getUser(final String name) throws IOException {
            if (name.contains("/")) {
                throw new IllegalArgumentException(
                        "User name contains a \"/\"");
            }
            return getUser(getEndpoint(USERS_ENDPOINT_PATH + "/" + name));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BitbucketUser getUser(final URI endpoint) throws IOException {
            HttpRequest request = requestFactory.buildGetRequest(
                    new GenericUrl(endpoint.toString()));
            request.setInterceptor(authentication);
            return getUser(request.execute());
        }
    }
}
