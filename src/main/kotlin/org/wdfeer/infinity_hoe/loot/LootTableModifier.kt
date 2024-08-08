package org.wdfeer.infinity_hoe.loot

import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.fabricmc.fabric.api.loot.v2.LootTableSource
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

object LootTableModifier {
    fun initialize() {
        LootTableEvents.MODIFY.register(::modifyLootTable)
    }

    // TODO: Create Tables and Rewards objects
    private val lootMap: Map<Identifier, LootPool.Builder> =
        Tables.easy.associateWith { Rewards.easy } + Tables.medium.associateWith { Rewards.medium } + Tables.hard.associateWith { Rewards.hard }

    private fun modifyLootTable(
        resourceManager: ResourceManager,
        lootManager: LootManager,
        id: Identifier,
        builder: LootTable.Builder,
        source: LootTableSource
    ) {
        if (!source.isBuiltin) return

        builder.pool(lootMap[id] ?: return)
    }
}