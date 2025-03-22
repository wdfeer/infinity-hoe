package org.wdfeer.infinity_hoe.loot

import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTables
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.function.EnchantRandomlyLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.Experience

private fun randomlyEnchantedLoot(item: Item, enchantCount: Int, chance: Float) = LootPool.builder()
    .with(getItemBuilder(item, enchantCount))
    .conditionally(RandomChanceLootCondition.builder(chance))

private fun getItemBuilder(item: Item, enchantments: Int): LeafEntry.Builder<*> {
    val builder = ItemEntry.builder(item)
        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f)))

    repeat(enchantments) { builder.apply(EnchantRandomlyLootFunction.create()) }

    return builder
}

internal enum class Tables(val tables: List<Identifier>, val reward: LootPool.Builder) {
    Stone(
        listOf(
            LootTables.SPAWN_BONUS_CHEST,
            LootTables.VILLAGE_TOOLSMITH_CHEST,
        ),
        randomlyEnchantedLoot(Items.STONE_HOE, 1, 1f)
    ),
    IronCommon(
        listOf(
            LootTables.IGLOO_CHEST_CHEST,
            LootTables.SHIPWRECK_MAP_CHEST,
            LootTables.SHIPWRECK_SUPPLY_CHEST,
            LootTables.VILLAGE_WEAPONSMITH_CHEST,
        ),
        randomlyEnchantedLoot(Items.IRON_HOE, 1, 0.4f)
    ),
    IronRare(
        listOf(
            LootTables.HERO_OF_THE_VILLAGE_FARMER_GIFT_GAMEPLAY,
            LootTables.ABANDONED_MINESHAFT_CHEST,
            LootTables.DESERT_PYRAMID_CHEST,
            LootTables.JUNGLE_TEMPLE_CHEST,
            LootTables.SHIPWRECK_TREASURE_CHEST,
            LootTables.BURIED_TREASURE_CHEST,
            LootTables.FISHING_TREASURE_GAMEPLAY,
            LootTables.WOODLAND_MANSION_CHEST
        ),
        randomlyEnchantedLoot(Items.IRON_HOE, 2, 0.25f)
    ),
    GoldCommon(
        listOf(
            LootTables.RUINED_PORTAL_CHEST,
            LootTables.BASTION_BRIDGE_CHEST,
            LootTables.BASTION_OTHER_CHEST,
            LootTables.NETHER_BRIDGE_CHEST,
        ),
        randomlyEnchantedLoot(Items.GOLDEN_HOE, 3, 0.2f)
    ),
    GoldRare(
        listOf(
            LootTables.BASTION_TREASURE_CHEST,
        ),
        randomlyEnchantedLoot(Items.GOLDEN_HOE, 5, 0.25f)
    ),
    Diamond(
        listOf(
            LootTables.ANCIENT_CITY_CHEST,
            LootTables.END_CITY_TREASURE_CHEST,
        ),
        randomlyEnchantedLoot(Items.DIAMOND_HOE, 4, 0.2f)
    ),
    Netherite(
        listOf(
            LootTables.ANCIENT_CITY_CHEST,
            LootTables.BASTION_TREASURE_CHEST,
        ),
        randomlyEnchantedLoot(Items.NETHERITE_HOE, 5, 0.03f)
    ),
    StrongholdLibrary(
        listOf(LootTables.STRONGHOLD_LIBRARY_CHEST),
        LootPool.builder().with(ItemEntry.builder(Items.IRON_HOE).apply(
            EnchantRandomlyLootFunction.create().add(Experience)
        ))
    )
}