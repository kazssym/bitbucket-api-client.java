/*
 * OAuthCredentials
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

import java.io.Serializable;

/**
 * Pair of an identifier and a shared secret for OAuth authorization.
 * @author Kaz Nishimura
 * @since 4.0
 * @deprecated As of version 5.0, replaced by simple string fields.
 */
@Deprecated
public class OAuthCredentials implements Serializable {

    private static final long serialVersionUID = 4L;

    private String id;

    private String secret;

    /**
     * Constructs this object with no property values.
     */
    public OAuthCredentials() {
    }

    /**
     * Constructs a credentials with an identifier and a secret.
     * This constructor is equivalent to the default one followed by calls to
     * {@link #setId} and {@link #setSecret}.
     * @param id identifier
     * @param secret secret
     */
    public OAuthCredentials(final String id, final String secret) {
        setId(id);
        setSecret(secret);
    }

    /**
     * Returns the identifier.
     * @return identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the secret.
     * @return secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the identifier.
     * @param value identifier to be set
     */
    public void setId(final String value) {
        id = value;
    }

    /**
     * Sets the secret.
     * @param value secret to be set
     */
    public void setSecret(final String value) {
        secret = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object.getClass() == OAuthCredentials.class) {
            OAuthCredentials credentials = (OAuthCredentials) object;
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
