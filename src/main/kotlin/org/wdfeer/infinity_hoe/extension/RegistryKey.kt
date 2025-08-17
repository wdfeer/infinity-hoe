package org.wdfeer.infinity_hoe.extension

import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.Registries
import net.minecraft.registry.entry.RegistryEntry

@Suppress("UNCHECKED_CAST")
fun <T> RegistryKey<T>.getValue(): T? {
    val registry = Registries.REGISTRIES[this.registry] as? Registry<T>
        ?: return null
    return registry.get(this.value)
}

@Suppress("UNCHECKED_CAST")
fun <T> RegistryKey<T>.getEntry(): RegistryEntry<T>? {
    val registry = Registries.REGISTRIES[this.registry] as? Registry<T>
        ?: return null
    return registry.getEntry(this).orElse(null)
}
