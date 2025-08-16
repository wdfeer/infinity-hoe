package org.wdfeer.infinity_hoe.enchantment.parent.charge

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Rarity
import org.wdfeer.infinity_hoe.event.listener.Usable

abstract class UsableHarvestChargeEnchantment() : HarvestChargeEnchantment(), Usable {
    protected abstract fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean

    protected open fun getCooldown() = 10

    final override fun use(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        if (useCharge(world, player, hoe)) {
            player.itemCooldownManager.set(hoe.item, getCooldown())
            setChargeWithSound(world, player, hoe, charge - getUsedCharge(charge))
        }
    }

    protected open fun getUsedCharge(charge: Int): Int = getChargeDecrement()

    override fun canAccept(other: Enchantment?): Boolean = other !is UsableHarvestChargeEnchantment
}