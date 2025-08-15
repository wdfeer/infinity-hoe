package org.wdfeer.infinity_hoe.client

import net.bettercombat.BetterCombatMod
import net.bettercombat.client.BetterCombatClientMod
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader

object InfinityHoeClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register { initBetterCombat() }
    }

    private fun initBetterCombat() {
        if (!FabricLoader.getInstance().isModLoaded(BetterCombatMod.ID)) return

        val currentPattern = BetterCombatClientMod.config.swingThruGrassBlacklist
        val hoeRegex = "hoe"

        val orPatterns: List<String> = currentPattern.split("|")
        if (!currentPattern.contains(hoeRegex) || orPatterns.none { it == "hoe" }) {
            val updatedPattern = "${currentPattern.trim('|')}|$hoeRegex"
            BetterCombatClientMod.config.swingThruGrassBlacklist = updatedPattern
        }
    }
}
