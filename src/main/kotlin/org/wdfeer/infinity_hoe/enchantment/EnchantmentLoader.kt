package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.slf4j.Logger
import org.wdfeer.infinity_hoe.enchantment.unique.combat.*
import org.wdfeer.infinity_hoe.enchantment.unique.common.*
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.*
import org.wdfeer.infinity_hoe.enchantment.unique.rare.*

object EnchantmentLoader {
    val infinity = Infinity()
    val pesticide = Pesticide()
    val growthAcceleration = GrowthAcceleration()
    val chainHarvest = ChainHarvest()
    val untill = Untill()

    val enchantments: List<HoeEnchantment> = listOf(
        infinity,
        pesticide,
        growthAcceleration,
        AutoSeed(),
        chainHarvest,
        untill,
        SoulSiphon(),
        Rejuvenation(),
        CropExperience(),
        Equinox(),
        MysticBlade()
    ) + BetterCombatEnchantment.enchantments + Specialist.enchantments


    fun initialize(logger: Logger?) {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }
        logger?.info("Loaded ${enchantments.size} hoe enchantments")


        GrowthAcceleration.initialize()
    }
}