package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.parent.chain.CalciumBurstAction
import org.wdfeer.infinity_hoe.enchantment.parent.chain.ChainEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.ChainHarvest
import org.wdfeer.infinity_hoe.event.listener.HarvestListener

object CalciumBurst : ChainEnchantment<CalciumBurstAction>(Rarity.UNCOMMON), HarvestListener {
    override val maxLvl: Int
        get() = 3
    override fun getPowerRange(level: Int): IntRange = ChainHarvest.getPowerRange(level)

    override fun getPath(): String = "calcium_burst"

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) manager.addAction(CalciumBurstAction(world, hoe, player, pos))
    }
}