package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.parent.chain.ChainHarvestAction
import org.wdfeer.infinity_hoe.enchantment.parent.chain.ChainEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener

object ChainHarvest : ChainEnchantment<ChainHarvestAction>(), HarvestListener {
    override fun getPath(): String = "chain_harvest"

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