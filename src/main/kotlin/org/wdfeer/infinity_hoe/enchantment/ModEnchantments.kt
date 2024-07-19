package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModEnchantments {
    val infinity = Infinity()
    val pesticide = Pesticide()

    val enchantments: List<HoeEnchantment> = listOf(
        infinity,
        pesticide
    )

    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }
    }
}