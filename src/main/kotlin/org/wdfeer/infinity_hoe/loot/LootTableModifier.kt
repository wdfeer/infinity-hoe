package org.wdfeer.infinity_hoe.loot

import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.fabricmc.fabric.api.loot.v3.LootTableSource
import net.minecraft.loot.LootTable
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.util.EnchantRandomlyWithoutEnchantmentConflictsLootFunction

object LootTableModifier {
    fun initialize() {
        EnchantRandomlyWithoutEnchantmentConflictsLootFunction.initialize()
        LootTableEvents.MODIFY.register { registryKey, builder, source, _ ->
            modifyLootTable(
                registryKey.value,
                builder,
                source
            )
        }
    }

    private fun modifyLootTable(
        id: Identifier,
        builder: LootTable.Builder,
        source: LootTableSource
    ) {
        if (!source.isBuiltin) return

        for (e in Tables.entries) {
            if (e.tables.any { key -> key.value == id }) {
                builder.pool(e.reward)
                return
            }
        }
    }
}