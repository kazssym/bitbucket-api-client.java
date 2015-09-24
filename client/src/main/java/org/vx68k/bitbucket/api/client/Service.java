/*
 * Service
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
import java.net.URI;
import java.util.UUID;

/**
 * Abstract Bitbucket API service.
 * <em>As of version 2.0, this class has been changed to abstract.</em>
 * @author Kaz Nishimura
 * @since 1.0
 */
public abstract class Service {

    /**
     * Indicates whether this object is authenticated or not.
     * @return <code>true</code> if this object is authenticated, or
     * <code>false</code> otherwise
     */
    public abstract boolean isAuthenticated();

    /**
     * Returns the current user.
     * @return current user, or <code>null</code> if this object is not
     * authenticated
     * @throws IOException if an I/O error has occurred
     * @since 2.0
     */
    public abstract BitbucketUser getCurrentUser() throws IOException;

    /**
     * Returns the Bitbucket user identified by a UUID.
     * @param uuid UUID of a Bitbucket user
     * @return Bitbucket user
     * @throws IOException if an I/O error has occurred
     * @since 2.0
     */
    public BitbucketUser getUser(UUID uuid) throws IOException {
        return getUser("%7B" + uuid.toString() + "%7D");
    }

    /**
     * Returns the Bitbucket user identified by a name.
     * @param name name of a Bitbucket user
     * @return Bitbucket user
     * @throws IOException if an I/O error has occurred
     * @since 2.0
     */
    public abstract BitbucketUser getUser(String name) throws IOException;

    /**
     * Returns the Bitbucket user from an endpoint.
     * @param endpoint endpoint URI
     * @return Bitbucket user
     * @throws IOException if an I/O error has occurred
     * @since 5.0
     */
    public abstract BitbucketUser getUser(URI endpoint) throws IOException;
}
