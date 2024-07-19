package org.wdfeer.infinity_hoe.util

import net.minecraft.enchantment.Enchantment
import net.minecraft.loot.function.EnchantRandomlyLootFunction.Builder

fun Builder.addAll(enchantments: Collection<Enchantment>): Builder {
    for (e in enchantments) {
        this.add(e)
    }
    return this
}