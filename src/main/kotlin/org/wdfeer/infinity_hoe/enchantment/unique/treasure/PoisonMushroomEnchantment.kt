package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

object PoisonMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE) {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "poison_mushroom"

    // TODO: Implement
}