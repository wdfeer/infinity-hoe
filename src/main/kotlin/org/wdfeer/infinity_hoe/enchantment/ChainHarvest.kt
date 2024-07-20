package org.wdfeer.infinity_hoe.enchantment

import com.google.common.math.IntMath.pow
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.util.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.hasEnchantment

class ChainHarvest : HoeEnchantment(Rarity.RARE) {
    companion object {
        fun initialize() {
            PlayerBlockBreakEvents.BEFORE.register { world, player, pos, state, _ -> preBlockBreak(world, player, pos, state); true }
        }

        private fun preBlockBreak(
            world: World?,
            player: PlayerEntity?,
            pos: BlockPos?,
            blockState: BlockState?,
        ) {
            if (world is ServerWorld && player is ServerPlayerEntity && pos != null && blockState?.block is CropBlock) {
                val stack = player.getStackInHand(player.activeHand)
                if (stack.item is HoeItem && stack.hasEnchantment(ModEnchantments.chainHarvest)){
                    trigger(world, player, pos, blockState.block as CropBlock, stack.getEnchantmentLevel(ModEnchantments.chainHarvest))
                }
            }
        }

        private fun trigger(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, blockType: CropBlock, level: Int) {
            val neighbors = pos.getAdjacentHorizontally(level + 2)
            neighbors.filter {
                world.getBlockState(it).block == blockType &&
                        blockType.getAge(world.getBlockState(it)) == blockType.maxAge }
                .shuffled()
                .take(getCropCount(level))
                .forEach { world.breakBlock(it, true, player) }
        }

        private fun getCropCount(level: Int) = pow(4, level)
    }
    
    override fun getPath(): String = "chain_harvest"

    override fun getMaxLevel(): Int = 3

    override fun getMinPower(level: Int): Int = 18 + level * 6

    override fun getMaxPower(level: Int): Int = 24 + level * 6
}