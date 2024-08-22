package org.wdfeer.infinity_hoe.enchantment.parent.charge

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.event.listener.Useable

abstract class UsableHarvestChargeEnchantment(rarity: Rarity) : HarvestChargeEnchantment(rarity), Useable {
    protected abstract fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean

    protected open fun getCooldown() = 10

    final override fun use(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        if (useCharge(world, player, hoe)) {
            player.itemCooldownManager.set(hoe.item, getCooldown())
            setCharge(hoe, charge - getChargeDecrement())
        }
    }

    override fun canAccept(other: Enchantment?): Boolean = other !is UsableHarvestChargeEnchantment
}