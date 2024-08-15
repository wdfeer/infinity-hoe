package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface AirUseListener {
    fun onUseInAir(world: ServerWorld, player: ServerPlayerEntity, stack: ItemStack)
}