package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

object HealMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE) {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "healing_mushroom"

    // TODO: Implement
}