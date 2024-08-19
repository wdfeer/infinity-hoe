package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.bc.BetterCombatEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.AutoSeed
import org.wdfeer.infinity_hoe.enchantment.unique.common.Untill
import org.wdfeer.infinity_hoe.enchantment.unique.rare.*
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.MinerHarvest
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.Rejuvenation
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.StandUnited
import org.wdfeer.infinity_hoe.enchantment.unique.very_rare.Blazing
import org.wdfeer.infinity_hoe.enchantment.unique.very_rare.Demolition

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
        MysticBlade(),
        MinerHarvest(),
        StandUnited(),
        Blazing(),
        Fleeting(),
        Demolition()
    ) + BetterCombatEnchantment.enchantments + Specialist.enchantments


    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityHoe.logger.info("Loaded ${enchantments.size} hoe enchantments")
    }
}