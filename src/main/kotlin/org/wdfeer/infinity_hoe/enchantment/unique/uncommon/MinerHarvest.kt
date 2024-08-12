package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener

class MinerHarvest : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    override fun getPowerRange(level: Int): IntRange = 10..50

    override fun getPath(): String = "miner_harvest"

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) {
        TODO("Implement functionality")
    }
}