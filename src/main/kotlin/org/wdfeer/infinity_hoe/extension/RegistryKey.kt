package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.entry.RegistryEntryOwner
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World

@Suppress("UNCHECKED_CAST")
fun <T> RegistryKey<T>.getValue(): T? {
    val registry = Registries.REGISTRIES[this.registry] as? Registry<T>
        ?: return null
    return registry.get(this.value)
}

@Suppress("UNCHECKED_CAST")
fun <T> RegistryKey<T>.getEntry(): RegistryEntry<T>? {
    assert(this.registry.path != "damage_type") { "Use getEntry(World) for damage types!" }

    val registry = Registries.REGISTRIES[this.registry] as? Registry<T>
        ?: return null
    return registry.getEntry(this.value).orElse(null)
}

fun RegistryKey<DamageType>.getEntry(world: World): RegistryEntry<DamageType> =
    world.registryManager.getEntryOrThrow(RegistryKeys.DAMAGE_TYPE).value().getEntry(this.value).get()

fun <T> RegistryKey<T>.getPlaceholderEntry(): RegistryEntry<T> =
    RegistryEntry.Reference.standAlone<T>(object : RegistryEntryOwner<T> {}, this)
