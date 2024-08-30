package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface Useable {
    fun use(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack)
}