package org.wdfeer.infinity_hoe.client

import net.bettercombat.client.BetterCombatClient
import net.fabricmc.api.ClientModInitializer

object InfinityHoeClient : ClientModInitializer {
    override fun onInitializeClient() {
        val currentPattern = BetterCombatClient.config.swingThruGrassBlacklist
        val hoeRegex = "\\bhoe\\b" // Regex for "hoe"

        if (!currentPattern.contains(hoeRegex)) {
            val updatedPattern = "$currentPattern|$hoeRegex".trim('|')
            BetterCombatClient.config.swingThruGrassBlacklist = updatedPattern
        }
    }
}