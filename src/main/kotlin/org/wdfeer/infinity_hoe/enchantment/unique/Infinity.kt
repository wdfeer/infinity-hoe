package org.wdfeer.infinity_hoe.enchantment.unique

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.chain.ActionManager
import org.wdfeer.infinity_hoe.enchantment.chain.InfinityTillAction

class Infinity : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "infinity"

    override fun getMaxLevel(): Int = 3
    override fun getMinPower(level: Int): Int = 18 + level * 6
    override fun getMaxPower(level: Int): Int = 24 + level * 6


    override fun isTreasure(): Boolean = true

    private val actionManager = ActionManager<InfinityTillAction>()

    override fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        if (blockTypes[hoe] != null)
            actionManager.addAction(InfinityTillAction(world, hoe, player, pos, blockTypes[hoe]!!))
        blockTypes.remove(hoe)
    }

    companion object {
        fun preTill(world: World, stack: ItemStack, blockPos: BlockPos) {
            if (world.isClient) return

            blockTypes[stack] = world.getBlockState(blockPos).block
        }

        private val blockTypes: MutableMap<ItemStack, Block> = mutableMapOf()
    }
}