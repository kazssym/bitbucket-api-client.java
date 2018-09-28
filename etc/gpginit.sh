#!/bin/sh
# gpginit.sh - GnuPG key initialization for BitbucketPipelines
# Copyright (C) 2018 Kaz Nishimura
#
# Copying and distribution of this file, with or without modification, are
# permitted in any medium without royalty provided the copyright notice and
# this notice are preserved.  This file is offered as-is, without any warranty.

if test -n "$GNUPGHOME"; then
    # Check for GnuPG 2.
    if gpg --dump-options | grep -qx -e "--pinentry-mode"; then
        (echo "pinentry-mode loopback") >> "$GNUPGHOME"/gpg.conf || exit 1;
    fi

    gpg --batch --import < etc/keys.asc || exit 1
fi

exit 0
