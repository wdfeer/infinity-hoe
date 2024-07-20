package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.enchantment.infinity.InfinityTiller

object ModEnchantments {
    val infinity = Infinity()
    val pesticide = Pesticide()
    val growthAcceleration = GrowthAcceleration()
    val autoSeed = AutoSeed()
    val chainHarvest = ChainHarvest()

    val enchantments: List<HoeEnchantment> = listOf(
        infinity,
        pesticide,
        growthAcceleration,
        autoSeed,
        chainHarvest
    )

    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityTiller.initialize()
        Pesticide.initialize()
        GrowthAcceleration.initialize()
        AutoSeed.initialize()
        ChainHarvest.initialize()
    }
}