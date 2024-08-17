package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.Emitters
import org.wdfeer.infinity_hoe.loot.LootTableModifier

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"
	val logger: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		EnchantmentLoader.initialize()
		Emitters.initialize()
		LootTableModifier.initialize()
	}
}