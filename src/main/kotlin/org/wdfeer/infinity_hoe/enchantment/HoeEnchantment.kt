package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID
import org.wdfeer.infinity_hoe.extension.getEntry

interface HoeEnchantment {
    val registryEntry: RegistryEntry<Enchantment>?
        get() = registryKey.getEntry()

    val registryKey: RegistryKey<Enchantment>
        get() = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, getPath()))

    fun getPath(): String
}