package org.wdfeer.infinity_hoe.extension

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.entry.RegistryEntryOwner
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import kotlin.jvm.optionals.getOrNull

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
fun <T> RegistryKey<T>.getEntry(world: ServerWorld): RegistryEntry<T>? {
    val registry =
        world.registryManager.getOptional(this.registryRef).getOrNull() ?: return null
    return registry.getEntry(this.value).orElse(null)
}

fun <T> RegistryKey<T>.getPlaceholderEntry(): RegistryEntry<T> =
    RegistryEntry.Reference.standAlone<T>(object : RegistryEntryOwner<T> {}, this)
