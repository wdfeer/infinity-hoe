package org.wdfeer.infinity_hoe.enchantment.catalyze

import com.google.common.math.IntMath.pow
import net.minecraft.block.CropBlock
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.getAdjacent
import org.wdfeer.infinity_hoe.util.randomRound
import org.wdfeer.infinity_hoe.util.randoms

object CropCatalyzer {
    fun trigger(
        world: ServerWorld,
        player: ServerPlayerEntity,
        level: Int,
        hoe: ItemStack,
        damageMult: Float = 1f
    ): Boolean {
        val blockCount = catalyzeGrowth(world, player.blockPos, level)
        if (blockCount > 0)
            hoe.damage(player, (getHoeDamage(blockCount) * damageMult).randomRound())

        return blockCount > 0
    }

    private fun getHoeDamage(blockCount: Int): Float = (blockCount + 2) / 3f

    private fun catalyzeGrowth(world: ServerWorld, pos: BlockPos, level: Int): Int {
        fun isApplicable(pos: BlockPos): Boolean {
            val state = world.getBlockState(pos)
            if (state.block !is CropBlock) return false

            val block = state.block as CropBlock
            return block.isFertilizable(world, pos, state, false)
        }

        fun apply(pos: BlockPos) {
            val state = world.getBlockState(pos)
            val block = state.block as CropBlock
            block.grow(world, world.random, pos, state)
        }

        pos.getAdjacent(level + 1)
            .filter(::isApplicable)
            .randoms(pow(level, 2) + 1)
            .apply {
                forEach(::apply)
                return size
            }
    }
}