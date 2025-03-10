package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.parent.chain.ChainEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.chain.InfinityTillAction
import org.wdfeer.infinity_hoe.event.listener.TillListener

object Infinity : ChainEnchantment<InfinityTillAction>(Rarity.COMMON), TillListener {
    override fun getPath(): String = "infinity"

    override val maxLvl: Int
        get() = ChainHarvest.MAX_LEVEL

    override fun getPowerRange(level: Int): IntRange = ChainHarvest.getPowerRange(level)

    override fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        if (blockTypes[hoe] != null)
            manager.addAction(InfinityTillAction(world, hoe, player, pos, blockTypes[hoe]!!))
        blockTypes.remove(hoe)
    }

    fun preTill(world: World, stack: ItemStack, blockPos: BlockPos) {
        if (world.isClient) return

        blockTypes[stack] = world.getBlockState(blockPos).block
    }

    private val blockTypes: MutableMap<ItemStack, Block> = mutableMapOf()
}