#!/bin/bash

# Get the text from the first argument
TEXT="$1"

# Input and output image
INPUT_IMAGE="src/main/resources/no_include/icon_base.png"
OUTPUT_IMAGE="src/main/resources/assets/infinity_hoe/icon.png"

# Font settings
FONT_SIZE=8
FONT_COLOR="white"
PADDING=10

# Use ImageMagick's convert command to add the text
magick "$INPUT_IMAGE" \
    -gravity southeast \
    -fill "$FONT_COLOR" \
    -pointsize "$FONT_SIZE" \
    -annotate +$PADDING+$PADDING "$TEXT" \
    "$OUTPUT_IMAGE"
