package org.wdfeer.infinity_hoe.enchantment

class GrowthAcceleration : HoeEnchantment(Rarity.UNCOMMON) {
    override fun getPath(): String = "growth_acceleration"

    override fun getMinPower(level: Int): Int = 10 + level * 3

    override fun getMaxPower(level: Int): Int = 16 + level * 5

    override fun getMaxLevel(): Int = 3
}