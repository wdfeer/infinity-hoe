package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.block.CropBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.event.listener.TillListener
import org.wdfeer.infinity_hoe.util.find

class AutoSeed : HoeEnchantment(Rarity.COMMON), HarvestListener, TillListener {
    override fun getPath(): String = "autoseed"

    override fun getPowerRange(level: Int): IntRange = 10..40

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) trigger(world, player, pos)
    }

    override fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        trigger(world, player, pos.up())
    }

    companion object {
        private fun trigger(
            world: ServerWorld,
            player: ServerPlayerEntity,
            pos: BlockPos,
        ) {
            val seed: ItemStack = findSeed(player) ?: return

            if (!player.canPlaceOn(pos, Direction.UP, seed)) return

            plant(world, seed, pos)
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
}