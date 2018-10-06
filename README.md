This file documents the Java Client Library for Bitbucket Cloud project.

# Introduction

This project builds the Java Client Library for Bitbucket Cloud.
It provides a client library for the Bitbucket API and a webhook library for
[Bitbucket Cloud].

[Bitbucket Cloud]: https://bitbucket.org/

[![(License)](https://img.shields.io/badge/license-AGPL--3.0--or--later-blue.svg)][AGPL-3.0]
[![(Open issues)](https://img.shields.io/bitbucket/issues/vx68k/bitbucket-api-client-java.svg)][open issues]
[![(Build Status)](https://linuxfront-functions.azurewebsites.net/api/bitbucket/build/vx68k/bitbucket-api-client-java?branch=master)][pipelines]

[AGPL-3.0]: https://opensource.org/licenses/AGPL-3.0 "GNU Affero General Public License v3.0"
[Open issues]: https://bitbucket.org/vx68k/bitbucket-api-client-java/issues?status=new&status=open
[Pipelines]: https://bitbucket.org/vx68k/bitbucket-api-client-java/addon/pipelines/home

## License

This program is *[free software][]*: you can redistribute it and/or modify it
under the terms of the *[GNU Affero General Public License][AGPL-3.0]* as published by
the [Free Software Foundation][], either version 3 of the License, or (at your
option) any later version.

[Free software]: <http://www.gnu.org/philosophy/free-sw.html> "What is free software?"
[Free Software Foundation]: <http://www.fsf.org/>

# Modules

The parent Maven project contains the following modules:

  * bitbucket-api-client – core library.
  * bitbucket-webhook – webhook library.
  * bitbucket-api-client-webapp – example web application that uses the libraries.
  * bitbucket-stubs – stub library for tests,
