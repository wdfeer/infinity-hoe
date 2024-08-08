package org.wdfeer.infinity_hoe.loot

import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

object LootTableModifier {
    fun initialize() {
        LootTableEvents.MODIFY.register(::modifyLootTable)
    }

    private fun modifyLootTable(
        resourceManager: ResourceManager,
        lootManager: LootManager,
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