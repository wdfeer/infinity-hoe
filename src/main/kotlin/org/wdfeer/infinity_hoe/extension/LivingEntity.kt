package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld

fun LivingEntity.damage(damageType: RegistryKey<DamageType>, amount: Float, attacker: LivingEntity) {
    val world = this.world as? ServerWorld ?: return
    val damageSource = DamageSource(damageType.getEntry(attacker.world), attacker)
    this.damage(world, damageSource, amount)
}