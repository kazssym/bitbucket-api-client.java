/*
 * Client - factory class for Bitbucket API sessions
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
import org.vx68k.bitbucket.api.client.util.Credentials;

/**
 * Bitbucket API client.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * Returns a Bitbucket API session using the client credentials.
     *
     * @return Bitbucket API session
     */
    public Session getSession() {
        return new Session(getCredentials());
    }
}
