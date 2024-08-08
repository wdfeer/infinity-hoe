package org.wdfeer.infinity_hoe.loot

import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTables
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.EnchantRandomlyLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.util.Identifier

internal enum class Tables(val tables: List<Identifier>, val reward: LootPool.Builder) {
    // TODO: reduce chances to something reasonable
    Easy(
        listOf(
            LootTables.SPAWN_BONUS_CHEST,
            LootTables.VILLAGE_DESERT_HOUSE_CHEST,
            LootTables.VILLAGE_SNOWY_HOUSE_CHEST,
            LootTables.VILLAGE_TAIGA_HOUSE_CHEST,
            LootTables.VILLAGE_SAVANNA_HOUSE_CHEST,
            LootTables.VILLAGE_PLAINS_CHEST,
            LootTables.IGLOO_CHEST_CHEST
        ),
        randomlyEnchantedLoot(Items.STONE_HOE, 1f)
    ),
    Medium(
        listOf(
            LootTables.HERO_OF_THE_VILLAGE_FARMER_GIFT_GAMEPLAY,
            LootTables.ABANDONED_MINESHAFT_CHEST,
            LootTables.DESERT_PYRAMID_CHEST,
            LootTables.JUNGLE_TEMPLE_CHEST,
            LootTables.BASTION_OTHER_CHEST,
            LootTables.NETHER_BRIDGE_CHEST,
        ),
        randomlyEnchantedLoot(Items.IRON_HOE, 1f)
    ),
    Hard(
        listOf(
            LootTables.BASTION_TREASURE_CHEST,
            LootTables.ANCIENT_CITY_CHEST,
            LootTables.END_CITY_TREASURE_CHEST
        ),
        randomlyEnchantedLoot(Items.DIAMOND_HOE, 1f)
    )
}

private fun randomlyEnchantedLoot(item: Item, chance: Float) = LootPool.builder()
    .with(
        ItemEntry.builder(item)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f)))
            .apply(EnchantRandomlyLootFunction.builder())
    )
    .conditionally(RandomChanceLootCondition.builder(chance))