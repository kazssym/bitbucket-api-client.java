#!/bin/sh
# gpginit.sh
# Copyright (C) 2018 Kaz Nishimura
#
# Copying and distribution of this file, with or without modification, are
# permitted in any medium without royalty provided the copyright notice and
# this notice are preserved.  This file is offered as-is, without any warranty.

# Check for GnuPG 2.
if gpg --dump-options | grep -qx -e "--pinentry-mode"; then
    (echo "pinentry-mode loopback") >> $GNUPGHOME/gpg.conf || exit 1;
fi

gpg --batch --import < etc/keys.asc || exit 1

exit 0
