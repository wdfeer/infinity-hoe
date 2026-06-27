package org.wdfeer.infinity_hoe.extension

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.entry.RegistryEntryOwner
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

@Suppress("UNCHECKED_CAST")
fun <T> RegistryKey<T>.get(): T? {
    val registry = Registries.REGISTRIES[this.registry] as? Registry<T>
        ?: return null
    return registry.get(this.value)
}

fun RegistryKey<Enchantment>.getHoeEnchantment(): HoeEnchantment? {
    return EnchantmentLoader.enchantments.find { it.registryKey.value == this.value }
}

@Suppress("UNCHECKED_CAST")
fun <T> RegistryKey<T>.getEntry(): RegistryEntry<T>? {
    val registry = Registries.REGISTRIES[this.registry] as? Registry<T>
        ?: return null
    return registry.getEntry(this).orElse(null)
}

fun <T> RegistryKey<T>.getPlaceholderEntry(): RegistryEntry<T> =
    RegistryEntry.Reference.standAlone<T>(object : RegistryEntryOwner<T> {}, this)
