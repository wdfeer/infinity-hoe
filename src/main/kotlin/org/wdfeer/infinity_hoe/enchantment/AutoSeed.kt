package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.block.CropBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class AutoSeed : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        fun onTill(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos) {
            val seed: ItemStack =
                player.handItems.find { it.item is BlockItem && (it.item as BlockItem).block is CropBlock } ?: return

            if (!player.canPlaceOn(pos, Direction.UP, seed)) return

            val block = (seed.item as BlockItem).block
            world.setBlockState(pos.up(), block.defaultState)

            seed.decrement(1)
        }
    }
    
    override fun getPath(): String = "autoseed"

    override fun getMinPower(level: Int): Int = 10

    override fun getMaxPower(level: Int): Int = 40
}