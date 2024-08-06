package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.HoeHarvest

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"
	private val logger: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		HoeHarvest.initialize()
		EnchantmentLoader.initialize(logger)
	}
}