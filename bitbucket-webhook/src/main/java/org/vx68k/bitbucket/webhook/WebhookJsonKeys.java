/*
 * WebhookJsonKeys
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

package org.vx68k.bitbucket.webhook;

/**
 * Collection of constant JSON keys.
 * @author Kaz Nishimura
 * @since 4.0
 */
public class WebhookJsonKeys {

    /**
     * JSON key for the <code>actor</code> object.
     */
    public static final String ACTOR = "actor";

    /**
     * JSON key for the <code>repository</code> object.
     */
    public static final String REPOSITORY = "repository";

    /**
     * JSON key for the <code>push</code> object.
     */
    public static final String PUSH = "push";

    /**
     * JSON key for the <code>links</code> object.
     */
    public static final String LINKS = "links";

    /**
     * JSON key for the <code>old</code> object.
     */
    public static final String OLD = "old";

    /**
     * JSON key for the <code>new</code> object.
     */
    public static final String NEW = "new";

    /**
     * JSON key for the <code>changes</code> array.
     */
    public static final String CHANGES = "changes";

    /**
     * JSON key for the <code>commits</code> array.
     */
    public static final String COMMITS = "commits";

    /**
     * JSON key for the <code>heads</code> array.
     */
    public static final String HEADS = "heads";

    /**
     * JSON key for the <code>created</code> value.
     */
    public static final String CREATED = "created";

    /**
     * JSON key for the <code>closed</code> value.
     */
    public static final String CLOSED = "closed";

    /**
     * JSON key for the <code>forced</code> value.
     */
    public static final String FORCED = "forced";

    /**
     * JSON key for the <code>name</code> value.
     */
    public static final String NAME = "name";

    protected WebhookJsonKeys() {
    }
}
