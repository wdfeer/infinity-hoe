package org.wdfeer.infinity_hoe.enchantment

class Infinity : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "infinity"

    override fun getMinPower(level: Int): Int = 20

    override fun getMaxPower(level: Int): Int = 50

    override fun isTreasure(): Boolean = true
}