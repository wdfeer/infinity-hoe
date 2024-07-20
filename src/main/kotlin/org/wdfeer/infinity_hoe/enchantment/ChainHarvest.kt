package org.wdfeer.infinity_hoe.enchantment

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
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
                    trigger(world, player, pos, blockState.block)
                }
            }
        }

        private fun trigger(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, blockType: Block) {
            TODO("Implement functionality")
        }
    }
    
    override fun getPath(): String = "chain_harvest"

    override fun getMinPower(level: Int): Int = 20

    override fun getMaxPower(level: Int): Int = 50
}