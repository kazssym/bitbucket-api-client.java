# READ ME

This directory contains the source code for the Bitbucket API Client Library
for Java.

The Bitbucket API Client Library provides classes that wraps [Bitbucket][]
REST API.

This program is *[free software][]*: you can redistribute it and/or modify it
under the terms of the *[GNU Affero General Public License][]* as published by
the [Free Software Foundation][], either version 3 of the License, or (at your
option) any later version.

[Bitbucket]: <https://bitbucket.org/>
[Free software]: <http://www.gnu.org/philosophy/free-sw.html> "What is free software?"
[GNU Affero General Public License]: <http://www.gnu.org/licenses/agpl.html>
[Free Software Foundation]: <http://www.fsf.org/>

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
`org.vx68k.bitbucket.api.client.example.id` to the client identifier (consumer
key) and `org.vx68k.bitbucket.api.client.example.secret` to the corresponding
client secret (consumer secret).
If you do not set the system properties, the values of environment variables
`BITBUCKET_CLIENT_ID` and `BITBUCKET_CLIENT_SECRET` will be used as the client
credentials instead, and it would be useful if you cannot set any Java system
properties for the web application.
