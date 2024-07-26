package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.HoeHarvest

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"

	override fun onInitialize() {
		EnchantmentLoader.initialize()
		HoeHarvest.initialize()
	}
}