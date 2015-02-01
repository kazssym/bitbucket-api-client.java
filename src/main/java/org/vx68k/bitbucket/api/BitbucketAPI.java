/*
 * BitbucketAPI - session factory for the Bitbucket API Client
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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.vx68k.bitbucket.api.util.Credentials;

/**
 * Factory object for Bitbucket API sessions.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
public class BitbucketAPI implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Credentials clientCredentials;

    public BitbucketAPI() {
        this.clientCredentials = new Credentials();
    }

    public BitbucketAPI(Credentials clientCredentials) {
        this.clientCredentials = new Credentials(clientCredentials);
    }

    public Credentials getClientCredentials() {
        return clientCredentials;
    }

    @Produces
    public BitbucketSession getSession() {
        return new BitbucketSession(getClientCredentials());
    }
}
