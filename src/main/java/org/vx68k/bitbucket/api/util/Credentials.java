/*
 * Credentials - credentials for the Bitbucket API Client.
 * Copyright (C) 2015 Kaz Nishimura
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

package org.vx68k.bitbucket.api.util;

import java.io.Serializable;
import javax.enterprise.context.Dependent;

/**
 * Pair of <code>ID</code> and its shared <code>secret</code>.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@Dependent
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ID;
    private String secret;

    public Credentials() {
        this("", "");
    }

    public Credentials(Credentials credentials) {
        this(credentials.getID(), credentials.getSecret());
    }

    public Credentials(String ID, String secret) {
        this.ID = ID;
        this.secret = secret;
    }

    public String getID() {
        return ID;
    }

    public String getSecret() {
        return secret;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
