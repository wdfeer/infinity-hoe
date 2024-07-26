package org.wdfeer.infinity_hoe.enchantment.chain

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.HoeHarvest

class ChainHarvestAction(world: ServerWorld, hoe: ItemStack, player: ServerPlayerEntity, origin: BlockPos, blockFilter: Block
) : FilterableAction(world, hoe, player, origin, blockFilter) {
    override fun isValidBlockState(state: BlockState): Boolean {
        return super.isValidBlockState(state) && (state.block as CropBlock).isMature(state)
    }

    override fun processBlock(pos: BlockPos) {
        val state = world.getBlockState(pos)
        world.breakBlock(pos, true, player)
        HoeHarvest.onCropBreak(world, player, pos, state, EnchantmentLoader.chainHarvest)
    }

    override fun getEnchantment(): HoeEnchantment = EnchantmentLoader.chainHarvest

    override fun canDamageHoe(): Boolean = false
}