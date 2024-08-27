package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.CropCatalyzer
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.random.Random

class GrowthAcceleration : HoeEnchantment(Rarity.UNCOMMON), PlayerTicker, CropCatalyzer {
    companion object {
        private const val TICK_INTERVAL: Long = 20
    }

    override fun getPath(): String = "growth_acceleration"

    override val maxLvl: Int
        get() = 3

    override fun getPowerRange(level: Int): IntRange = 8 + level * 5..16 + level * 5


    override fun canIteratePlayers(world: ServerWorld) = world.time % TICK_INTERVAL == 0L

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val regen = player.getStatusEffect(StatusEffects.REGENERATION)?.amplifier

        if (!Random.roll(getPlayerTickChance(regen))) return

        val hoe = player.handItems.find { it.item is HoeItem } ?: return
        val level = hoe.getEnchantmentLevel(EnchantmentLoader.growthAcceleration)

        if (level == -1) return

        catalyze(world, player, level, hoe)
    }

    private fun getPlayerTickChance(regen: Int?): Float = 0.1f + (regen ?: 0) * 0.02f
}