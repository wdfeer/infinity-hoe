package org.wdfeer.infinity_hoe.extension

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.entry.RegistryEntry

// TODO: I have no clue whether this works

fun <T> RegistryKey<T>.getValue(): T? =
    (Registries.REGISTRIES[this.registry] as Registry<T>)[this.value]

fun <T> RegistryKey<T>.getEntry(): RegistryEntry<T> =
    (Registries.REGISTRIES[this.registry] as Registry<T>).entryOf(this)
