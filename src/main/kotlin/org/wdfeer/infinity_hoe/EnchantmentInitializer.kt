package org.wdfeer.infinity_hoe

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.enchantment.*
import org.wdfeer.infinity_hoe.enchantment.common.HoeEnchantment

object EnchantmentInitializer {
    val infinity = Infinity()
    val pesticide = Pesticide()
    val growthAcceleration = GrowthAcceleration()
    private val autoSeed = AutoSeed()
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

        Infinity.initialize()
        GrowthAcceleration.initialize()
        ChainHarvest.initialize()
    }
}