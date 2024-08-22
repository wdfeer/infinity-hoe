package org.wdfeer.infinity_hoe.enchantment.chain

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.world.ServerWorld

class ActionManager<T : ChainAction<Any?>> {
    init {
        ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
    }

    private val map: MutableMap<ServerWorld, MutableList<T>> = mutableMapOf()

    private fun onWorldTick(world: ServerWorld?) {
        map[world]?.forEach { it.tick() }
        map[world]?.removeIf { !it.isActive() }
    }

    fun addAction(action: T) = map.getOrPut(action.world) { mutableListOf() }.add(action)
}