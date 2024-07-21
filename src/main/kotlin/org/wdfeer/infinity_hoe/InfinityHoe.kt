package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.wdfeer.infinity_hoe.event.CropBreakListener

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"

	override fun onInitialize() {
		EnchantmentInitializer.initialize()
		CropBreakListener.initialize()
	}
}