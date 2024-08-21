package org.wdfeer.infinity_hoe.enchantment.demeter

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

object HoeOwnerTracker {
    init {
        ServerTickEvents.END_WORLD_TICK.register(::postWorldTick)
    }

    fun getOwner(hoe: ItemStack): ServerPlayerEntity? = hoeToPlayer[hoe]

    private val hoeToPlayer: MutableMap<ItemStack, ServerPlayerEntity> = mutableMapOf()

    private fun postWorldTick(world: ServerWorld) {
        world.players.forEach { player ->
            repeat(player.inventory.size()) {
                itemTick(player, player.inventory.getStack(it))
            }
        }
    }

    private fun itemTick(player: ServerPlayerEntity, stack: ItemStack) {
        if (stack.item is HoeItem)
            hoeToPlayer[stack] = player
    }
}