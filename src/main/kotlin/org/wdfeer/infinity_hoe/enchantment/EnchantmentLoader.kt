package org.wdfeer.infinity_hoe.enchantment

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.bc.BetterCombatEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.*
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.*
import org.wdfeer.infinity_hoe.enchantment.unique.rare.*
import org.wdfeer.infinity_hoe.enchantment.unique.very_rare.Blazing
import java.io.File

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
        Fleeting()
    ) + BetterCombatEnchantment.enchantments + Specialist.enchantments


    fun initialize() {
        for (enchantment in enchantments) {
            Registry.register(Registries.ENCHANTMENT, enchantment.getIdentifier(), enchantment)
        }

        InfinityHoe.logger.info("Loaded ${enchantments.size} hoe enchantments")

        if (FabricLoader.getInstance().isDevelopmentEnvironment)
            saveIcongenEnvironmentVariable()
    }

    private fun saveIcongenEnvironmentVariable() {
        try {
            val file = File("../scripts/.env")
            if (!file.isFile) file.createNewFile()

            file.printWriter().use { it.println("ENCHANTMENT_COUNT=${enchantments.size}") }
            InfinityHoe.logger.debug("Env file for icongen successfully written")
        } catch (e : Exception) {
            InfinityHoe.logger.error("Failed writing the env file for icongen!")
            e.printStackTrace()
        }
    }
}