package org.wdfeer.infinity_hoe.enchantment.charge

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel

abstract class HarvestChargeEnchantment(rarity: Rarity) : ChargeEnchantment(rarity), HarvestListener {
    final override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!mature) return

        val charge = getCharge(hoe)
        val level = hoe.getEnchantmentLevel(this)

        if (charge < getMaxCharge(level)) setCharge(hoe, charge + 1)
    }
}