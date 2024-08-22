package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.server.network.ServerPlayerEntity

interface PlayerDamageTaken {
    fun preDamageTaken(player: ServerPlayerEntity, amount: Float)
}