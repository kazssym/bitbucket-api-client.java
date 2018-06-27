/*
 * EventRecorder.java - class EventRecorder
 * Copyright (C) 2018 Kaz Nishimura
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

package org.vx68k.bitbucket.api.client.example;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.vx68k.bitbucket.webhook.BitbucketEvent;

/**
 * Managed bean to record Bitbucket events.
 *
 * @author Kaz Nishimura
 * @since 5.0
 */
@ApplicationScoped
public class EventRecorder implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Records a Bitbucket event.
     *
     * @param event Bitbucket event to record
     */
    public void record(@Observes BitbucketEvent event)
    {
        // @todo Implement this method.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int code = getClass().hashCode();
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object)
    {
        if (this != object) {
            if (object == null || object.getClass() != getClass()) { // NOPMD
                return false;
            }
        }
        return true;
    }
}
