#!/bin/sh
# setupkeys.sh - set up keys for signing
# Copyright (C) 2020 Kaz Nishimura
#
# Copying and distribution of this file, with or without modification, are
# permitted in any medium without royalty provided the copyright notice and
# this notice are preserved.  This file is offered as-is, without any warranty.

export GNUPGHOME=${GNUPGHOME:-$HOME/.gnupg}

mkdir -p -m go-rwx "$GNUPGHOME"
echo allow-preset-passphrase >> "$GNUPGHOME"/gpg-agent.conf || exit $?

gpg --import --batch "$1" || exit $?

keygrip=`gpg --list-secret-keys --with-colons "$GPG_KEYNAME" | \
    awk -F : '$1 == "grp" { print $10 }'`
exec /usr/lib/gnupg/gpg-preset-passphrase --preset "$keygrip"
