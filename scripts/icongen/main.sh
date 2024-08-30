#!/bin/bash

./scripts/icongen/createEnv.sh

source "scripts/icongen/.env"
source "scripts/icongen/config.sh"

source "scripts/icongen/static/main.sh"
source "scripts/icongen/animated/main.sh"
