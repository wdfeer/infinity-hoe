package org.wdfeer.infinity_hoe.enchantment.demeter

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

interface DemeterEnchantment {
    fun getPlayerHarvestCount(world: ServerWorld, player: ServerPlayerEntity): Int {
        val playerData = DemeterState.getServerState(world.server)[player.uuid] ?: return 0
        return playerData.values.sum()
    }
}