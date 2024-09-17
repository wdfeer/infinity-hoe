package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker

class LunaDial : UsableHarvestChargeEnchantment(Rarity.VERY_RARE), PlayerTicker {
    override fun canIteratePlayers(world: ServerWorld): Boolean {
        TODO("Not yet implemented")
    }

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        TODO("Not yet implemented")
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 50

    override fun getPowerRange(level: Int): IntRange = 30..100

    override fun getPath(): String = "luna_dial"
}