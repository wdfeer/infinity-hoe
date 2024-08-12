package org.wdfeer.infinity_hoe.enchantment.double_harvest

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.ItemEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

object DoubleHarvest {
    fun drop(world: ServerWorld, state: BlockState, pos: BlockPos) {
        getDroppedItemEntities(world, state, pos).forEach { world.spawnEntity(it) }
    }

    private fun getDroppedItemEntities(
        world: ServerWorld,
        state: BlockState,
        pos: BlockPos,
    ): List<ItemEntity> {
        return Block.getDroppedStacks(state, world, pos, null).map { drop ->
            ItemEntity(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), drop)
        }
    }
}