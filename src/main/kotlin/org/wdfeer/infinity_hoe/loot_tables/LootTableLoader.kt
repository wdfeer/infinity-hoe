package org.wdfeer.infinity_hoe.loot_tables

import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTables
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.EnchantRandomlyLootFunction
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.enchantment.Infinity
import org.wdfeer.infinity_hoe.enchantment.Pesticide


object LootTableLoader {
    fun initialize() {
        LootTableEvents.MODIFY.register { _, _, id, builder, source -> modifyLootTables(id, builder, source) }
    }

    private fun modifyLootTables(
        id: Identifier?,
        tableBuilder: LootTable.Builder?,
        source: LootTableSource
    ) {
        if (source.isBuiltin && LootTables.WOODLAND_MANSION_CHEST == id && tableBuilder != null) {
            val pool: LootPool = LootPool.builder()
                .with(
                    ItemEntry.builder(Items.DIAMOND_HOE)
                        .apply(
                            EnchantRandomlyLootFunction.create()
                                .add(Enchantments.UNBREAKING)
                                .add(Enchantments.MENDING)
                                .add(Infinity.instance)
                                .add(Pesticide.instance)
                        )
                        .build()
                ).with(
                    ItemEntry.builder(Items.ENCHANTED_BOOK)
                        .apply(
                            EnchantRandomlyLootFunction.create()
                                .add(Infinity.instance)
                                .add(Pesticide.instance)
                        )
                        .build()
                )
                .build()
            tableBuilder.pool(pool)
        }
    }
}