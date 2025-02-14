package org.wdfeer.infinity_hoe.enchantment.parent.chain

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.Infinity
import org.wdfeer.infinity_hoe.event.emitter.HoeUse

class InfinityTillAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos,
    blockFilter: Block
) : FilterableAction(world, hoe, player, origin, blockFilter) {
    override fun processBlock(pos: BlockPos) {
        world.setBlockState(pos, Blocks.FARMLAND.defaultState)

        HoeUse.onTill(world, player, hoe, pos, Infinity)
    }

    override fun getEnchantment(): HoeEnchantment = Infinity
}