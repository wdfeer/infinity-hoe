package org.wdfeer.infinity_hoe.enchantment.double_harvest

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.ItemEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.chain.ChainEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.AutoSeed
import org.wdfeer.infinity_hoe.event.emitter.HoeHarvest

interface DoubleHarvestEnchantment {
    fun drop(world: ServerWorld, player: ServerPlayerEntity, state: BlockState, pos: BlockPos) {
        getDroppedItemEntities(world, state, pos).forEach { world.spawnEntity(it) }

        HoeHarvest.onCropBreak(world, player, pos, state) {
            it !is DoubleHarvestEnchantment && it !is ChainEnchantment<*> && it !is AutoSeed
        }
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