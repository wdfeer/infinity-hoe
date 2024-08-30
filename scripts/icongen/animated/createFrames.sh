IFS=';' read -r -a enchantment_array <<< "$ENCHANTMENTS"

function drawText() {
    magick "$1" -gravity NorthWest -pointsize 12 -fill white -annotate +0+"$3" "$2" "$4"
}

function createFrame() {
  output_path="$TMP_PATH/frame_$1.png"

  cp "$BASE_IMAGE" "$output_path"

  for k in "${!enchantment_array[@]}"; do
      enchantment="${enchantment_array[$k]}"

      y=$(( ($1-k)*12 ))

      if [ $y -ge 0 ] && [ $y -le 160 ]; then
        drawText "$output_path" "$enchantment" "$y" "$output_path"
      fi
  done
}

for i in {1..44} ; do
    createFrame "$i"
    echo "Created frame #$i"
done