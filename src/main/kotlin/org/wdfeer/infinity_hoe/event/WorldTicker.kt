package org.wdfeer.infinity_hoe.event

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.listener.TickListener

object WorldTicker {
    fun initialize() {
        ServerTickEvents.END_WORLD_TICK.register(::postWorldTick)
    }

    private val tickListeners: List<TickListener> = EnchantmentLoader.enchantments.filterIsInstance<TickListener>()

    private fun postWorldTick(serverWorld: ServerWorld) {
        tickListeners.forEach { it.postWorldTick(serverWorld) }
    }
}