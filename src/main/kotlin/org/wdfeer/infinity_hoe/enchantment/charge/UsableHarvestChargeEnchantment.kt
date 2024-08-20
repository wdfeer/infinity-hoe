package org.wdfeer.infinity_hoe.enchantment.charge

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.event.listener.AirUseListener

abstract class UsableHarvestChargeEnchantment(rarity: Rarity) : HarvestChargeEnchantment(rarity), AirUseListener {
    protected abstract fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean

    protected open fun getCooldown() = 10

    final override fun onUseInAir(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        if (useCharge(world, player, hoe)) {
            player.itemCooldownManager.set(hoe.item, getCooldown())
            setCharge(hoe, charge - getChargeDecrement())
        }
    }

    override fun canAccept(other: Enchantment?): Boolean = other !is UsableHarvestChargeEnchantment
}