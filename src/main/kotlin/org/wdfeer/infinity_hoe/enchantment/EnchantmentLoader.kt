package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.parent.BetterCombatEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.*
import org.wdfeer.infinity_hoe.enchantment.unique.rare.*
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.*
import org.wdfeer.infinity_hoe.enchantment.unique.very_rare.*

object EnchantmentLoader {
    val enchantments: List<HoeEnchantment> = listOf(
        Infinity,
        Pesticide,
        GrowthAcceleration,
        AutoSeed,
        ChainHarvest,
        Untill,
        SoulSiphon,
        Rejuvenation,
        CropExperience,
        Equinox,
        MysticBlade,
        MinerHarvest,
        StandUnited,
        Blazing,
        Fleeting,
        Demolition,
        CalciumBurst,
        DemeterWrath,
        DemeterAegis,
        DemeterGrace,
        PoisonNova,
        Automata,
        Decompose,
        FrostNova,
        HealingNova,
        Leap,
        LunaDial,
        EquivalentExchange,
        CursedForge,
        AnimalBlessing,
        BlessedForge,
        MiracleBlessing
    ) + BetterCombatEnchantment.enchantments + Specialist.enchantments


    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityHoe.logger.info("Loaded ${enchantments.size} hoe enchantments")
    }
}