package org.wdfeer.infinity_hoe.enchantment

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.HoeListener
import org.wdfeer.infinity_hoe.util.find
import org.wdfeer.infinity_hoe.util.hasEnchantment

class AutoSeed : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        fun initialize() {
            HoeListener.listen(::onTill)
            PlayerBlockBreakEvents.AFTER.register { world, player, pos, state, _ -> onBlockBreak(world, player, pos, state) }
        }

        private fun onBlockBreak(
            world: World?,
            player: PlayerEntity?,
            pos: BlockPos?,
            blockState: BlockState?,
        ) {
            if (world is ServerWorld && player is ServerPlayerEntity && pos != null && blockState?.block is CropBlock) {
                val stack = player.getStackInHand(player.activeHand)
                if (stack.item is HoeItem && stack.hasEnchantment(ModEnchantments.autoSeed)){
                    val seed: ItemStack = findSeed(player) ?: return

                    if (!player.canPlaceOn(pos, Direction.UP, seed)) return

                    plant(world, seed, pos)
                }
            }

        }

        private fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
            if (hoe.hasEnchantment(ModEnchantments.autoSeed)) {
                val seed: ItemStack = findSeed(player) ?: return

                if (!player.canPlaceOn(pos, Direction.UP, seed)) return

                plant(world, seed, pos.up())
            }
        }

        private fun findSeed(player: ServerPlayerEntity): ItemStack? {
            fun predicate(stack: ItemStack) = stack.item is BlockItem && (stack.item as BlockItem).block is CropBlock
            return player.handItems.find(::predicate) ?: player.inventory.find(::predicate)
        }


        private fun plant(world: ServerWorld, seed: ItemStack, pos: BlockPos) {
            val block = (seed.item as BlockItem).block
            world.setBlockState(pos, block.defaultState)

            seed.decrement(1)
        }
    }
    
    override fun getPath(): String = "autoseed"

    override fun getMinPower(level: Int): Int = 10

    override fun getMaxPower(level: Int): Int = 40
}