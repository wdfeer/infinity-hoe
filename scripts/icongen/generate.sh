#!/bin/bash

# Get the text from the first argument
TEXT="$1"

# Input and output image
INPUT_IMAGE="base.png"
OUTPUT_IMAGE="output.png"

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
