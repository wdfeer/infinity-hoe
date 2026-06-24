package org.wdfeer.infinity_hoe.loot

import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTables
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.registry.RegistryKey
import org.wdfeer.infinity_hoe.util.EnchantRandomlyWithoutEnchantmentConflictsLootFunction

private fun randomlyEnchantedLoot(item: Item, enchantCount: Int, chance: Float): LootPool.Builder {
    fun getItemBuilder(item: Item, enchantCount: Int): LeafEntry.Builder<*> {
        val builder = ItemEntry.builder(item)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f)))

        repeat(enchantCount) { builder.apply(EnchantRandomlyWithoutEnchantmentConflictsLootFunction.Builder()) }

        return builder
    }

    return LootPool.builder()
        .with(getItemBuilder(item, enchantCount))
        .conditionally(RandomChanceLootCondition.builder(chance))
}

internal enum class Tables(val tables: List<RegistryKey<LootTable>>, val reward: LootPool.Builder) {
    Wood(
        listOf(
            LootTables.SPAWN_BONUS_CHEST,
            LootTables.VILLAGE_TOOLSMITH_CHEST,
        ),
        randomlyEnchantedLoot(Items.WOODEN_HOE, 1, 0.6f)
    ),
    Stone(
        listOf(
            LootTables.SPAWN_BONUS_CHEST,
            LootTables.VILLAGE_TOOLSMITH_CHEST,
            LootTables.TRIAL_CHAMBERS_ENTRANCE_CHEST,
        ),
        randomlyEnchantedLoot(Items.STONE_HOE, 1, 0.4f)
    ),
    IronCommon(
        listOf(
            LootTables.IGLOO_CHEST_CHEST,
            LootTables.SHIPWRECK_MAP_CHEST,
            LootTables.SHIPWRECK_SUPPLY_CHEST,
            LootTables.VILLAGE_WEAPONSMITH_CHEST,
            LootTables.HERO_OF_THE_VILLAGE_FARMER_GIFT_GAMEPLAY,
            LootTables.STRONGHOLD_CROSSING_CHEST,
        ),
        randomlyEnchantedLoot(Items.IRON_HOE, 1, 0.4f)
    ),
    IronRare(
        listOf(
            LootTables.HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT_GAMEPLAY,
            LootTables.STRONGHOLD_LIBRARY_CHEST,
            LootTables.ABANDONED_MINESHAFT_CHEST,
            LootTables.DESERT_PYRAMID_CHEST,
            LootTables.JUNGLE_TEMPLE_CHEST,
            LootTables.SHIPWRECK_TREASURE_CHEST,
            LootTables.BURIED_TREASURE_CHEST,
            LootTables.FISHING_TREASURE_GAMEPLAY,
            LootTables.WOODLAND_MANSION_CHEST,
            LootTables.TRIAL_CHAMBERS_INTERSECTION_CHEST,
        ),
        randomlyEnchantedLoot(Items.IRON_HOE, 2, 0.25f)
    ),
    GoldCommon(
        listOf(
            LootTables.RUINED_PORTAL_CHEST,
            LootTables.BASTION_BRIDGE_CHEST,
            LootTables.BASTION_OTHER_CHEST,
            LootTables.NETHER_BRIDGE_CHEST,
            LootTables.TRIAL_CHAMBERS_REWARD_COMMON_CHEST
        ),
        randomlyEnchantedLoot(Items.GOLDEN_HOE, 3, 0.2f)
    ),
    GoldRare(
        listOf(
            LootTables.BASTION_TREASURE_CHEST,
            LootTables.TRIAL_CHAMBERS_REWARD_RARE_CHEST,
            LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON_CHEST
        ),
        randomlyEnchantedLoot(Items.GOLDEN_HOE, 5, 0.25f)
    ),
    Diamond(
        listOf(
            LootTables.ANCIENT_CITY_CHEST,
            LootTables.END_CITY_TREASURE_CHEST,
            LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE_CHEST
        ),
        randomlyEnchantedLoot(Items.DIAMOND_HOE, 4, 0.2f)
    ),
    Netherite(
        listOf(
            LootTables.ANCIENT_CITY_CHEST,
            LootTables.BASTION_TREASURE_CHEST,
            LootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE_CHEST
        ),
        randomlyEnchantedLoot(Items.NETHERITE_HOE, 5, 0.03f)
    )
}