package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID

interface HoeEnchantment {
    // TODO: I have no clue whether this works
    val registryEntry: RegistryEntry<Enchantment>
        get() = RegistryEntry.of((Registries.REGISTRIES[RegistryKeys.ENCHANTMENT.value] as Registry<Enchantment>)[registryKey]!!)

    val registryKey: RegistryKey<Enchantment>
        get() = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, getPath()))

    fun getPath(): String
}