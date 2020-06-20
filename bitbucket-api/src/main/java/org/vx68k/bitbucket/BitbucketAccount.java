/*
 * BitbucketAccount.java
 * Copyright (C) 2018-2020 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.vx68k.bitbucket;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Account on Bitbucket Cloud.
 * A Bitbucket Cloud account is either of a user or of a team.
 * This interface can represent both types of accounts, but the attributes
 * specific to user accounts are not accessible with this interface but with
 * {@link BitbucketUserAccount}.
 *
 * @author Kaz Nishimura
 * @see BitbucketUserAccount
 * @since 6.0
 */
public interface BitbucketAccount
{
    enum AccountType
    {
        USER("user"), TEAM("team");

        private final String label;

        private AccountType(final String label)
        {
            this.label = label;
        }

        @Override
        public final String toString()
        {
            return label;
        }
    }

    /**
     * Return the type of the account.
     *
     * @return the type of the account
     */
    AccountType getType();

    /**
     * Returns the UUID of the account.
     *
     * @return the UUID of the account
     */
    UUID getUuid();

    /**
     * Returns the name of the account.
     * This property identifies the account.
     *
     * @return the name of the account
     */
    String getName();

    /**
     * Returns the display name of the account.
     *
     * @return the display name of the account
     */
    String getDisplayName();

    /**
     * Returns the website URI of the account.
     *
     * @return the website URI of the account
     */
    String getWebsite();

    /**
     * Returns the location of the account.
     *
     * @return the location of the account
     */
    String getLocation();

    /**
     * Returns the date and time when the account was created.
     *
     * @return the date and time when the account was created
     */
    OffsetDateTime getCreated();

    /**
     * Returns a map to the links of the account
     *
     * @return a map to the links of the account
     */
    Map<String, URI> getLinks();
}
