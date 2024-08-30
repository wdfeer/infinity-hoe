package org.wdfeer.infinity_hoe.enchantment.parent.chain

import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class InfinityUntillAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos
) : FilterableAction(world, hoe, player, origin, Blocks.FARMLAND) {
    override fun processBlock(pos: BlockPos) { world.setBlockState(pos, Blocks.DIRT.defaultState) }
    
    override fun getEnchantment(): HoeEnchantment = EnchantmentLoader.infinity
}