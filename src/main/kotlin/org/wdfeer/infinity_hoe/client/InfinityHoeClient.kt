package org.wdfeer.infinity_hoe.client

import net.bettercombat.BetterCombat
import net.bettercombat.client.BetterCombatClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader

object InfinityHoeClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register { initBetterCombat() }
    }

    private fun initBetterCombat() {
        if (!FabricLoader.getInstance().isModLoaded(BetterCombat.MODID)) return

        val currentPattern = BetterCombatClient.config.swingThruGrassBlacklist
        val hoeRegex = "hoe"

        val orPatterns: List<String> = currentPattern.split("|")
        if (!currentPattern.contains(hoeRegex) || orPatterns.none { it == "hoe" }) {
            val updatedPattern = "${currentPattern.trim('|')}|$hoeRegex"
            BetterCombatClient.config.swingThruGrassBlacklist = updatedPattern
        }
    }
}
