package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.tilling.InfinityTiller

object ModEnchantments {
    val infinity = Infinity()
    val pesticide = Pesticide()
    val growthAcceleration = GrowthAcceleration()
    val autoSeed = AutoSeed()

    val enchantments: List<HoeEnchantment> = listOf(
        infinity,
        pesticide,
        growthAcceleration,
        autoSeed
    )

    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityTiller.initialize()
        GrowthAcceleration.initialize()
        AutoSeed.initialize()
    }
}