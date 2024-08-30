#!/bin/bash

# Split the enchantments into an array
IFS=';' read -r -a enchantment_array <<< "$ENCHANTMENTS"

# Temporary directory for frames
mkdir -p "scripts/icongen/animated/tmp"

# TODO: Implement frame creation

magick -delay 10 -loop 1 "scripts/icongen/animated/tmp/frame_*.png" "$OUTPUT_GIF"

rm -rf "scripts/icongen/animated/tmp"

echo "Created animated icon at $OUTPUT_GIF"
