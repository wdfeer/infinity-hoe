package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import com.google.common.math.IntMath.pow
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.CropBlock
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.*
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

                    val level: Int = player.handItems.filter { it.item is HoeItem }
                        .maxOfOrNull { it.getEnchantmentLevel(EnchantmentLoader.growthAcceleration) }
                        ?: continue

                    growthAccelerationTick(world, player, level)
                }
        }

        fun growthAccelerationTick(world: ServerWorld, player: PlayerEntity, level: Int) {
            fun isApplicable(pos: BlockPos): Boolean {
                val state = world.getBlockState(pos)
                if (state.block !is CropBlock) return false

                val block = state.block as CropBlock
                return block.isFertilizable(world, pos, state, false)
            }

            fun apply(pos: BlockPos) {
                val state = world.getBlockState(pos)
                val block = state.block as CropBlock
                block.grow(world, player.random, pos, state)
            }

            player.blockPos.getAdjacent(level + 1)
                .filter(::isApplicable)
                .randoms(pow(level, 2) + 1)
                .forEach(::apply)
        }
    }

    override fun getPath(): String = "growth_acceleration"

    override fun getMinPower(level: Int): Int = 8 + level * 5

    override fun getMaxPower(level: Int): Int = 16 + level * 5

    override fun getMaxLevel(): Int = 3
}