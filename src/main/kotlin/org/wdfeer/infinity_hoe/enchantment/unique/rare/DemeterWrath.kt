package org.wdfeer.infinity_hoe.enchantment.unique.rare

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class DemeterWrath : HoeEnchantment(Rarity.RARE) {
    override fun getPowerRange(level: Int): IntRange = 20 ..50

    override fun getPath(): String = "demeter_wrath"
}