package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.extension.hasEnchantment

interface HoldTicker : PlayerTicker {
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val stack = player.handItems.find {
            !it.isEmpty && it.hasEnchantment(this as? HoeEnchantment ?: return)
        } ?: return
        
        holdTick(world, player, stack)
    }
    
    fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack)
}