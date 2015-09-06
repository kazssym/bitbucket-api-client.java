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

/**
 * Pair of an identifier and a shared secret.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String secret;

    /**
     * Constructs this object without initialization.
     */
    public Credentials() {
    }

    /**
     * Constructs a credentials with an identifier and a shared secret.
     * @param id identifier
     * @param secret shared secret
     */
    public Credentials(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    /**
     * Returns the identifier of this object.
     * @return identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the shared secret of this object.
     * @return shared secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the identifier of this object.
     * @param id identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the shared secret of this object.
     * @param secret shared secret.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object.getClass() == Credentials.class) {
            Credentials credentials = (Credentials) object;
            if (id != null) {
                if (!id.equals(credentials.id)) {
                    return false;
                }
            } else {
                if (credentials.id != null) {
                    return false;
                }
            }
            if (secret != null) {
                if (!secret.equals(credentials.secret)) {
                    return false;
                }
            } else {
                if (credentials.secret != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int code = getClass().hashCode();
        if (id != null) {
            code ^= id.hashCode();
        }
        if (secret != null) {
            code ^= secret.hashCode();
        }
        return code;
    }
}
