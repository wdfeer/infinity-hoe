package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.parent.BetterCombatEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.AutoSeed
import org.wdfeer.infinity_hoe.enchantment.unique.common.ChainHarvest
import org.wdfeer.infinity_hoe.enchantment.unique.common.Infinity
import org.wdfeer.infinity_hoe.enchantment.unique.common.Untill
import org.wdfeer.infinity_hoe.enchantment.unique.rare.*
import org.wdfeer.infinity_hoe.enchantment.unique.treasure.HealMushroomEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.treasure.PoisonMushroomEnchantment
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
        Experience,
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
        MiracleBlessing,
        Fireblast,
        FungusEnchanter,
        PoisonMushroomEnchantment,
        HealMushroomEnchantment
    ) + BetterCombatEnchantment.enchantments + Specialist.enchantments + GrowingCapital.getSelfIfCanRegister


    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityHoe.logger.info("Loaded ${enchantments.size} hoe enchantments")
    }
}