package org.wdfeer.infinity_hoe.enchantment.chain

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.HoeUse
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class InfinityTillAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos,
    blockFilter: Block
) : FilterableAction(world, hoe, player, origin, blockFilter) {
    override fun getInitialPower(): Int =
        ((hoe.item.maxDamage - hoe.damage) / 2 + 1) * (hoe.getEnchantmentLevel(Enchantments.UNBREAKING) + 1)

    override fun processBlock(pos: BlockPos) {
        world.setBlockState(pos, Blocks.FARMLAND.defaultState)

        HoeUse.onTill(world, player, hoe, pos, EnchantmentLoader.infinity)
    }
}