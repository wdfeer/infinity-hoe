package org.wdfeer.infinity_hoe.extension

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey

// TODO: I have no clue whether this works
fun <T> RegistryKey<T>.getValue(): T? =
    (Registries.REGISTRIES[this.registry] as Registry<T>)[this.value]
