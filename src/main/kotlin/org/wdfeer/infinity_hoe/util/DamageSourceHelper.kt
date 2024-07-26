package org.wdfeer.infinity_hoe.util

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.world.World

object DamageSourceHelper {
    fun create(world: World, type: RegistryKey<DamageType>): DamageSource =
        DamageSource(DamageTypeHelper.getRegistryEntry(world, type))

    fun create(world: World, type: RegistryKey<DamageType>, entity: Entity) =
        DamageSource(DamageTypeHelper.getRegistryEntry(world, type), entity)
}