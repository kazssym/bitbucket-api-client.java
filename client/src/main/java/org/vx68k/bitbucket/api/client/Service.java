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

import java.io.Serializable;
import java.util.Date;
import com.google.api.client.auth.oauth2.TokenResponse;

/**
 * Bitbucket API service.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class Service {

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Expiration time of the access token.
     */
    private Date expiration;

    /**
     * Refresh token, or <code>null</code> if not specified.
     */
    private String refreshToken;

    public Service(TokenResponse tokenResponse) {
        if (tokenResponse != null) {
            accessToken = tokenResponse.getAccessToken();

            Long expiresIn = tokenResponse.getExpiresInSeconds();
            if (expiresIn != null) {
                Date now = new Date();
                expiration = new Date(now.getTime() + expiresIn * 1000L);
            }
            refreshToken = tokenResponse.getRefreshToken();
        }
    }
}
