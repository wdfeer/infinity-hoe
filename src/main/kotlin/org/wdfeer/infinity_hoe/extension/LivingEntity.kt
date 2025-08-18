package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.util.DamageSourceHelper

fun LivingEntity.damage(damageType: RegistryKey<DamageType>, amount: Float, attacker: LivingEntity) {
    this.damage(world as ServerWorld,DamageSourceHelper.create(this.world, damageType, attacker), amount)
}