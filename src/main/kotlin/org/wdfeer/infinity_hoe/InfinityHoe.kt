package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"

	override fun onInitialize() {
		Infinity.register()
		ChainTiller.initialize()
	}
}