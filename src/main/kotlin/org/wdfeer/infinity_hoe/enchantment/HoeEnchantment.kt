package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID

enum class EnchantmentRarity(val weight: Int) {
    Common(10),
    Uncommon(5),
    Rare(3),
    VeryRare(1)
}

interface HoeEnchantment {
    fun getName(): String
    val rarity: EnchantmentRarity
    fun getPowerRange(level: Int): IntRange
    val maxLevel: Int

    val registryKey: RegistryKey<Enchantment> get() = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, getName()))
}