package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.enchantment.unique.*

object EnchantmentLoader {
    val infinity = Infinity()
    val pesticide = Pesticide()
    val growthAcceleration = GrowthAcceleration()
    private val autoSeed = AutoSeed()
    val chainHarvest = ChainHarvest()
    val untill = Untill()
    private val soulSiphon = SoulSiphon()

    val enchantments: List<HoeEnchantment> = listOf(
        infinity,
        pesticide,
        growthAcceleration,
        autoSeed,
        chainHarvest,
        untill,
        soulSiphon
    ) + Specialist.enchantments


    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        GrowthAcceleration.initialize()
    }
}