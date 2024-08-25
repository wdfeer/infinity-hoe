package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class Automata : HoeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 25..60

    override fun getPath(): String = "automata"

    // TODO: Implement functionality
}