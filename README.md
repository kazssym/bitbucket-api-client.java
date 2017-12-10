# READ ME

This directory contains the source code for the Bitbucket API Client for Java.

The Bitbucket API Client for Java library provides Java classes to use the
[Bitbucket][] REST API.

This program is *[free software][]*: you can redistribute it and/or modify it
under the terms of the *[GNU Affero General Public License][]* as published by
the [Free Software Foundation][], either version 3 of the License, or (at your
option) any later version.

[![(License)](https://img.shields.io/badge/license-AGPL--3.0+-blue.svg)][AGPL-3.0]
[![(Release Version)](https://img.shields.io/maven-central/v/org.vx68k.bitbucket/bitbucket-api-client.svg)][org.vx68k.bitbucket:bitbucket-api-client]
[![(Build Status)](https://linuxfront-functions.azurewebsites.net/api/bitbucket/build/vx68k/bitbucket-api-client-java?branch=master)][vx68k/bitbucket-api-client-java]

[Bitbucket]: <https://bitbucket.org/>
[Free software]: <http://www.gnu.org/philosophy/free-sw.html> "What is free software?"
[GNU Affero General Public License]: <http://www.gnu.org/licenses/agpl.html>
[Free Software Foundation]: <http://www.fsf.org/>
[AGPL-3.0]: https://opensource.org/licenses/AGPL-3.0 "GNU Affero General Public License v3.0"
[org.vx68k.bitbucket:bitbucket-api-client]: https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.vx68k.bitbucket%22%20AND%20a%3A%22bitbucket-api-client%22
[vx68k/bitbucket-api-client-java]: https://bitbucket.org/vx68k/bitbucket-api-client-java

## Modules

The parent Maven project consists of the following modules:

 * client – core library,
 * client-oauth – OAuth support library that provides an OAuth redirection
   endpoint servlet,
 * example – example web application that uses the libraries.

## Configuring the example web application

The example web application can be configured with Bitbucket OAuth client
credentials by Java system properties or by environment variables.
If it is configured with the client credentials, a Login item will appear at
the top-right corner of the web pages and users can log in with their
Bitbucket accounts, though they can do nothing yet but log in.

To configure the client credentials by Java system properties, set
`org.vx68k.bitbucket.api.client.example.oauth.clientId` to the client
identifier (consumer key) and
`org.vx68k.bitbucket.api.client.example.oauth.clientSecret` to the
corresponding client secret (consumer secret).
If you do not set the system properties, the values of environment variables
`BITBUCKET_CLIENT_ID` and `BITBUCKET_CLIENT_SECRET` will be used as the client
credentials instead, and it would be useful if you cannot set any system
properties for the web application.
