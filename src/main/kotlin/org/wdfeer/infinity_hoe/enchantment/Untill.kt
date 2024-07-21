package org.wdfeer.infinity_hoe.enchantment

import org.wdfeer.infinity_hoe.enchantment.common.HoeEnchantment

class Untill : HoeEnchantment(Rarity.COMMON) {
    override fun getPath(): String = "untill"

    override fun getMinPower(level: Int): Int = 10

    override fun getMaxPower(level: Int): Int = 60
}