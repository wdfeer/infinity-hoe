package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.chain.ActionManager
import org.wdfeer.infinity_hoe.enchantment.chain.ChainHarvestAction
import org.wdfeer.infinity_hoe.enchantment.common.HoeEnchantment

class ChainHarvest : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "chain_harvest"

    override fun getMaxLevel(): Int = 3

    override fun getMinPower(level: Int): Int = 18 + level * 6

    override fun getMaxPower(level: Int): Int = 24 + level * 6


    private val actionManager = ActionManager<ChainHarvestAction>()

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) {
        if (!mature) return

        actionManager.addAction(ChainHarvestAction(world, hoe, player, pos, state.block))
    }
}