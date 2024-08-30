#!/bin/bash

TMP_PATH="scripts/icongen/animated/tmp"

mkdir -p $TMP_PATH

. "scripts/icongen/animated/createFrames.sh"

magick -delay 10 -dispose Previous "scripts/icongen/animated/tmp/frame_*.png" -loop 1 "$OUTPUT_GIF"

rm -rf $TMP_PATH

echo "Created animated icon at $OUTPUT_GIF"
