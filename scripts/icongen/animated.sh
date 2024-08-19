#!/bin/bash

# Split the enchantments into an array
IFS=';' read -r -a enchantment_array <<< "$ENCHANTMENTS"

# Temporary directory for frames
mkdir -p "scripts/icongen/tmp"

text_x_offset=10
text_y_start=0
text_y_step=1

frame_number=0
for enchantment in "${enchantment_array[@]}"; do
  for (( y_offset=text_y_start; y_offset<128; y_offset+=text_y_step )); do
    frame_path="scripts/icongen/tmp/frame_$frame_number.png"

    magick "$OUTPUT_PNG" -gravity northwest -pointsize 24 -fill white -annotate +"$text_x_offset"+"$y_offset" "$enchantment" "$frame_path"

    ((frame_number++))
  done
done

magick -delay 10 -loop 1 "scripts/icongen/tmp/frame_*.png" "$OUTPUT_GIF"

#rm -rf "scripts/icongen/tmp"

echo "GIF created: $OUTPUT_GIF"
