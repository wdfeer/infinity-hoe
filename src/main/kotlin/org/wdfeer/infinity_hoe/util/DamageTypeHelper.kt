package org.wdfeer.infinity_hoe.util

import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.world.World

object DamageTypeHelper {
    fun getRegistryEntry(world: World, key: RegistryKey<DamageType>): RegistryEntry<DamageType> =
        world.registryManager.getEntryOrThrow(RegistryKeys.DAMAGE_TYPE).value().getEntry(key.value).get()
}
