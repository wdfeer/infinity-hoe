package org.wdfeer.infinity_hoe.enchantment.unique.common

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class DemeterWrath : HoeEnchantment(Rarity.COMMON) {
    override fun getPowerRange(level: Int): IntRange = 10 ..50

    override fun getPath(): String = "demeter_wrath"
}