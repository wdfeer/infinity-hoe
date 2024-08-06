package org.wdfeer.infinity_hoe.enchantment.unique.combat

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class MysticBlade : HoeEnchantment(Rarity.RARE) {
    override fun getPowerRange(level: Int): IntRange = 10..50

    override fun getPath(): String = "mystic_blade"
}