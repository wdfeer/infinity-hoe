#!/bin/bash

./scripts/icongen/parseLang.sh

source "scripts/icongen/.env"
source "scripts/icongen/config.sh"

source "scripts/icongen/static.sh"
source "scripts/icongen/animated.sh"