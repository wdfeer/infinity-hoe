package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe

class Infinity : HoeEnchantment(Rarity.RARE) {
    companion object {
        val instance = Infinity()

        fun register() {
            Registry.register(Registries.ENCHANTMENT, Identifier(InfinityHoe.MOD_ID, "infinity"), instance)
        }
    }

    override fun getMinPower(level: Int): Int = 20

    override fun getMaxPower(level: Int): Int = 50

    override fun isTreasure(): Boolean = true
}