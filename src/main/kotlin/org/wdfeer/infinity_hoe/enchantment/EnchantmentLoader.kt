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
    val infinity = Infinity()
    val pesticide = Pesticide()
    val growthAcceleration = GrowthAcceleration()
    val chainHarvest = ChainHarvest()
    val untill = Untill()
    val calciumBurst = CalciumBurst()
    val automata = Automata()

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
        Demolition(),
        calciumBurst,
        DemeterWrath(),
        DemeterAegis(),
        DemeterGrace(),
        PoisonNova(),
        automata,
        Decompose(),
        FrostNova(),
        HealingNova(),
        Leap(),
        LunaDial()
    ) + BetterCombatEnchantment.enchantments + Specialist.enchantments


    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityHoe.logger.info("Loaded ${enchantments.size} hoe enchantments")
    }
}