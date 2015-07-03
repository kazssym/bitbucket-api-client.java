/*
 * Credentials - credentials for the Bitbucket API Client Library
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
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.HttpExecuteInterceptor;

/**
 * Pair of an identifier and a shared secret.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ID;
    private String secret;

    /**
     * Constructs an empty credentials.
     */
    public Credentials() {
        this(null, null);
    }

    /**
     * Constructs a credentials with an ID and a shared secret.
     *
     * @param ID identifier of the credentials
     * @param secret shared secret of the credentials
     */
    public Credentials(String ID, String secret) {
        this.ID = ID;
        this.secret = secret;
    }

    /**
     * Returns the identifier of this object.
     *
     * @return identifier
     */
    public String getID() {
        return ID;
    }

    /**
     * Returns the shared secret of this object.
     *
     * @return shared secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the identifier of this object.
     *
     * @param ID identifier
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Sets the shared secret of this object.
     *
     * @param secret shared secret.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Returns <code>true</code> if this object is empty.
     *
     * @return <code>true</code> if this object is empty, or <code>false</code>
     * otherwise.
     */
    public boolean isEmpty() {
        return getID() == null && getSecret() == null;
    }

    public HttpExecuteInterceptor getClientAuthentication() {
        if (isEmpty()) {
            return null;
        }
        return new ClientParametersAuthentication(getID(), getSecret());
    }
}
