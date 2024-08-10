package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface PlayerTicker : TickListener {
    fun canIteratePlayers(world: ServerWorld): Boolean
    fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity)

    override fun postWorldTick(world: ServerWorld) {
        if (canIteratePlayers(world))
            world.players.forEach { tickPlayer(world, it) }
    }
}