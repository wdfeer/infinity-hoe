package org.wdfeer.infinity_hoe.client

import net.bettercombat.BetterCombatMod
import net.bettercombat.client.BetterCombatClientMod
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents
import net.fabricmc.loader.api.FabricLoader

object InfinityHoeClient : ClientModInitializer {
    override fun onInitializeClient() {
        CommonLifecycleEvents.TAGS_LOADED.register { _, _ ->
            initBetterCombat() // TODO: check whether it has the same functionality as CLIENT_STARTED event from 1.21.1
        }
//        ClientLifecycleEvents.CLIENT_STARTED.register { initBetterCombat() }

        HoeTooltip.initialize()
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
