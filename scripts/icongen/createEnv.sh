#!/bin/bash

LANG_PATH="src/main/resources/assets/infinity_hoe/lang/en_us.json"
ENV_FILE="scripts/icongen/.env"

# Extract the enchantment names
ENCHANTMENTS=$(jq -r 'to_entries[] | select(.key | match("enchantment\\.infinity_hoe\\.[^.]+$")) | .value' "$LANG_PATH")

# Count the number of enchantments
ENCHANTMENT_COUNT=$(echo "$ENCHANTMENTS" | wc -l)

# Join enchantment names with ';' and prepare ENCHANTMENTS variable
ENCHANTMENTS_JOINED=$(echo "$ENCHANTMENTS" | paste -sd ';' -)

# Write to .env file
{
  echo "ENCHANTMENT_COUNT=$ENCHANTMENT_COUNT"
  echo "ENCHANTMENTS=\"$ENCHANTMENTS_JOINED\""
} > "$ENV_FILE"

echo "Created .env with $ENCHANTMENT_COUNT enchantments"