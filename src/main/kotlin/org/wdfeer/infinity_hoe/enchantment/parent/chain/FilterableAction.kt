package org.wdfeer.infinity_hoe.enchantment.parent.chain

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

abstract class FilterableAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos,
    private val blockFilter: Block
) : ChainAction(world, hoe, player, origin) {
    override fun getRequiredBlock(): Block = blockFilter
}