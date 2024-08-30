#!/bin/bash

magick "$BASE_IMAGE" \
    -gravity southeast \
    -fill "$FONT_COLOR" \
    -pointsize 64 \
    -font "$FONT" \
    -annotate -5-5 "$ENCHANTMENT_COUNT" \
    "$OUTPUT_PNG"

echo "Created static icon"