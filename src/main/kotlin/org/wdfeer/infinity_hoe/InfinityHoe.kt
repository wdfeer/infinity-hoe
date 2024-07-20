package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.wdfeer.infinity_hoe.enchantment.ModEnchantments
import org.wdfeer.infinity_hoe.event.CropBreakListener
import org.wdfeer.infinity_hoe.loot_tables.LootTableLoader

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"

	override fun onInitialize() {
		ModEnchantments.initialize()
		CropBreakListener.initialize()
		LootTableLoader.initialize()
	}
}