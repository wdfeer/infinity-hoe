package org.wdfeer.infinity_hoe

import net.fabricmc.api.ModInitializer
import org.wdfeer.infinity_hoe.enchantment.ModEnchantments
import org.wdfeer.infinity_hoe.loot_tables.LootTableLoader
import org.wdfeer.infinity_hoe.tilling.ChainTiller

object InfinityHoe : ModInitializer {
	const val MOD_ID: String = "infinity_hoe"

	override fun onInitialize() {
		ModEnchantments.initialize()
		ChainTiller.initialize()
		LootTableLoader.initialize()
	}
}