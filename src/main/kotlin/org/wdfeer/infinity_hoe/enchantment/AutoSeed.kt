package org.wdfeer.infinity_hoe.enchantment

class AutoSeed : HoeEnchantment(Rarity.UNCOMMON) {
    override fun getPath(): String = "autoseed"

    override fun getMinPower(level: Int): Int = 10

    override fun getMaxPower(level: Int): Int = 40
}