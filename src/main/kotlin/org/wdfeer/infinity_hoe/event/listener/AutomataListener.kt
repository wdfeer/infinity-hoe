package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.entity.ItemEntity
import net.minecraft.server.world.ServerWorld

interface AutomataListener {
    fun postAutomataTick(world: ServerWorld, hoe: ItemEntity)
}