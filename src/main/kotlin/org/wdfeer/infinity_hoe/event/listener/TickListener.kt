package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.server.world.ServerWorld

interface TickListener {
    fun postWorldTick(world: ServerWorld)
}