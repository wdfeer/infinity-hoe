package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.fabricmc.loader.api.FabricLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class Reaper : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        fun canRegister(): Boolean = FabricLoader.getInstance().isModLoaded("bettercombat")
    }

    override fun getPath(): String = "reaper"

    override fun getMinPower(level: Int): Int = 8
    override fun getMaxPower(level: Int): Int = 40
}