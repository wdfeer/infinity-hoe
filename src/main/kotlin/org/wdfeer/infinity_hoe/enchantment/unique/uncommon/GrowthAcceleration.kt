package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.HoeItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.catalyze.CropCatalyzer.trigger
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.getStatusLevel
import org.wdfeer.infinity_hoe.util.roll
import kotlin.random.Random

class GrowthAcceleration : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        private const val TICK_INTERVAL: Long = 20

        private fun getPlayerTickChance(regen: Int?): Float = 0.1f + (regen ?: 0) * 0.01f

        fun initialize() {
            ServerTickEvents.END_WORLD_TICK.register(Companion::onWorldTick)
        }

        private fun canIteratePlayers(world: World): Boolean = world.time % TICK_INTERVAL == 0L

        private fun onWorldTick(world: ServerWorld) {
            if (canIteratePlayers(world))
                for (player in world.players){
                    if (!player.isAlive) continue

                    val regen = player.getStatusLevel(StatusEffects.REGENERATION)

                    if (!Random.roll(getPlayerTickChance(regen))) continue

                    val hoe = player.handItems.find { it.item is HoeItem } ?: continue
                    val level = hoe.getEnchantmentLevel(EnchantmentLoader.growthAcceleration)

                    if (level == -1) continue

                    trigger(world, player, level, hoe)
                }
        }


    }

    override fun getPath(): String = "growth_acceleration"

    override val maxLvl: Int
        get() = 3

    override fun getPowerRange(level: Int): IntRange = 8 + level * 5..16 + level * 5
}