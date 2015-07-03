/*
 * Session - session for the Bitbucket API
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
 * Bitbucket API session.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Credentials clientCredentials;

    /**
     * Token credentials for this session.
     */
    private Credentials tokenCredentials = null;

    public Session(Credentials clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    public Credentials getTokenCredentials() {
        return tokenCredentials;
    }

    public void setTokenCredentials(Credentials tokenCredentials) {
        this.tokenCredentials = tokenCredentials;
    }
}
