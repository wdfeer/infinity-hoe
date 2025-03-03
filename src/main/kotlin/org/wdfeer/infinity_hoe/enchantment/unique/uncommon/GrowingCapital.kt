package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.fabricmc.loader.api.FabricLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

object GrowingCapital : HoeEnchantment(Rarity.UNCOMMON) {
    val getSelfIfCanRegister
        get() = if (FabricLoader.getInstance().isModLoaded("numismatic_overhaul")) listOf(this) else emptyList()

    override fun getPowerRange(level: Int): IntRange = 15..45
    override fun getPath(): String = "growing_capital"
}