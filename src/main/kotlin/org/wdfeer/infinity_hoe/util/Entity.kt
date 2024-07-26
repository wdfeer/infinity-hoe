package org.wdfeer.infinity_hoe.util

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey

fun Entity.damage(damageType: RegistryKey<DamageType>, amount: Float) {
    this.damage(DamageSourceHelper.create(this.world, damageType), amount)
}

fun Entity.damage(damageType: RegistryKey<DamageType>, amount: Float, attacker: Entity) {
    this.damage(DamageSourceHelper.create(this.world, damageType, attacker), amount)
}