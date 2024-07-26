package org.wdfeer.infinity_hoe.enchantment.chain

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class InfinityUntillAction(world: ServerWorld, hoe: ItemStack, player: ServerPlayerEntity, origin: BlockPos
) : ChainAction(world, hoe, player, origin) {
    override fun getInitialPower(): Int =
        ((hoe.item.maxDamage - hoe.damage) / 2 + 1) * (hoe.getEnchantmentLevel(Enchantments.UNBREAKING) + 1)

    override fun processBlock(pos: BlockPos) { world.setBlockState(pos, Blocks.DIRT.defaultState) }

    override fun getRequiredBlock(): Block = Blocks.FARMLAND
}