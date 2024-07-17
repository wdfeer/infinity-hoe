package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object InfinityHoe : ModInitializer {
    private val logger = LoggerFactory.getLogger("infinity_hoe")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}
}