package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ToolItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.CropCatalyzer
import org.wdfeer.infinity_hoe.enchantment.unique.rare.AnimalBlessing
import org.wdfeer.infinity_hoe.enchantment.unique.very_rare.MiracleBlessing
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.incrementBounds
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.random.Random

object GrowthAcceleration : HoeEnchantment(Rarity.UNCOMMON), PlayerTicker, CropCatalyzer {
    override fun getPath(): String = "growth_acceleration"

    override val maxLvl: Int
        get() = 3

    override fun getPowerRange(level: Int): IntRange = (8..16).incrementBounds(level * 5)

    override fun canIteratePlayers(world: ServerWorld) = world.time % 20 == 0L

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val regen = player.getStatusEffect(StatusEffects.REGENERATION)?.amplifier

        if (!Random.roll(getPlayerTickChance(regen))) return

        val tool = player.handItems.find {
            // not HoeItem because BlessedForge applies this enchantment to non-hoes as well
            it.item is ToolItem
        } ?: return
        val level = tool.getEnchantmentLevel(GrowthAcceleration)

        if (level == -1) return

        catalyze(world, player, level, tool)
    }

    private fun getPlayerTickChance(regen: Int?): Float = 0.1f + (regen ?: 0) * 0.02f

    override fun canAccept(other: Enchantment?): Boolean =
        super.canAccept(other) && other !is AnimalBlessing && other !is MiracleBlessing
}