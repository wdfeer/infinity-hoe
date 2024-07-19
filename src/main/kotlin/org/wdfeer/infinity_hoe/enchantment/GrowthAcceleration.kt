package org.wdfeer.infinity_hoe.enchantment

import com.google.common.math.IntMath.pow
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Fertilizable
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.util.getAdjacent
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.randoms

class GrowthAcceleration : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        private const val INTERVAL: Long = 200

        fun initialize() {
            ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
        }

        private fun canIteratePlayers(world: World): Boolean = world.time % INTERVAL == 0L

        private fun onWorldTick(world: ServerWorld) {
            if (canIteratePlayers(world))
                for (player in world.players){
                    if (!player.isAlive) continue

                    val level: Int = player.handItems.filter { it.item is HoeItem }
                        .maxOfOrNull { it.getEnchantmentLevel(ModEnchantments.growthAcceleration) }
                        ?: 0

                    if (level > 0) {
                        growthAccelerationTick(world, player, level)
                    }
                }
        }

        private fun growthAccelerationTick(world: ServerWorld, player: PlayerEntity, level: Int) {
            fun isApplicable(pos: BlockPos): Boolean {
                val state = world.getBlockState(pos)
                if (state.block !is Fertilizable) return false

                val block = state.block as Fertilizable
                return block.isFertilizable(world, pos, state, false)
            }

            fun apply(pos: BlockPos) {
                val state = world.getBlockState(pos)
                val block = state.block as Fertilizable
                block.grow(world, player.random, pos, state)
            }

            player.blockPos.getAdjacent(level * 2)
                .filter(::isApplicable)
                .randoms(pow(level, 3))
                .forEach(::apply)
        }
    }

    override fun getPath(): String = "growth_acceleration"

    override fun getMinPower(level: Int): Int = 10 + level * 3

    override fun getMaxPower(level: Int): Int = 16 + level * 5

    override fun getMaxLevel(): Int = 3
}