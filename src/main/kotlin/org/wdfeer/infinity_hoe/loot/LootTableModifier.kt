package org.wdfeer.infinity_hoe.loot

import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.loot.LootTable
import net.minecraft.util.Identifier

object LootTableModifier {
    fun initialize() {
        LootTableEvents.MODIFY.register { _, _, id, builder, source -> modifyLootTable(id, builder, source) }
    }

    private fun modifyLootTable(
        id: Identifier,
        builder: LootTable.Builder,
        source: LootTableSource
    ) {
        if (!source.isBuiltin) return

        for (e in Tables.entries) {
            if (e.tables.contains(id)) {
                builder.pool(e.reward)
                return
            }
        }
    }
}