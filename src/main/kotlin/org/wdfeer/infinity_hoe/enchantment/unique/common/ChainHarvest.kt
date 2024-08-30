package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.parent.chain.ChainHarvestAction
import org.wdfeer.infinity_hoe.enchantment.parent.chain.ChainEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.incrementBounds

class ChainHarvest : ChainEnchantment<ChainHarvestAction>(Rarity.COMMON), HarvestListener {
    override fun getPath(): String = "chain_harvest"

    override val maxLvl: Int
        get() = MAX_LEVEL

    override fun getPowerRange(level: Int): IntRange = Companion.getPowerRange(level)

    companion object {
        const val MAX_LEVEL = 3
        fun getPowerRange(level: Int): IntRange = (15..21).incrementBounds(level * 6)
    }


    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) {
        if (!mature) return

        manager.addAction(ChainHarvestAction(world, hoe, player, pos, state.block))
    }
}