/*
 * BitbucketSession - session for the Bitbucket API Client
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

package org.vx68k.bitbucket.api;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import org.vx68k.bitbucket.api.util.Credentials;

/**
 * Session for the Bitbucket API.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
public class BitbucketSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Credentials clientCredentials;

    /**
     * Token credentials for this session.
     */
    private Credentials tokenCredentials = null;

    public BitbucketSession(Credentials clientCredentials) {
        this.clientCredentials = new Credentials(clientCredentials);
    }

    public Credentials getTokenCredentials() {
        return tokenCredentials;
    }

    public void setTokenCredentials(Credentials tokenCredentials) {
        this.tokenCredentials = tokenCredentials;
    }
}
