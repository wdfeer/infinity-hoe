package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld

fun LivingEntity.damage(damageType: RegistryKey<DamageType>, amount: Float, attacker: LivingEntity) {
    val world = this.entityWorld as? ServerWorld ?: return
    val entry = damageType.getEntry(attacker.entityWorld)
    val damageSource = DamageSource(entry, attacker)
    this.damage(world, damageSource, amount)
}