package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import kotlin.math.min

fun LivingEntity.getStatusPotency(effect: StatusEffect): Int = this.getStatusEffect(effect)?.amplifier ?: -1
fun LivingEntity.getStatusDuration(effect: StatusEffect): Int = this.getStatusEffect(effect)?.duration ?: 0

fun LivingEntity.stackStatusPotency(effect: StatusEffect, duration: Int, max: Int, increment: Int = 1) {
    val instance = this.getStatusEffect(effect)
    if (instance == null)
        addStatusEffect(StatusEffectInstance(effect, duration, -1 + increment))
    else
    {
        val potency = min(instance.amplifier + increment, max)
        addStatusEffect(StatusEffectInstance(effect, duration, potency))
    }
}

fun LivingEntity.stackStatusDuration(effect: StatusEffect, potency: Int, max: Int, increment: Int) {
    val instance = this.getStatusEffect(effect)
    if (instance == null)
        addStatusEffect(StatusEffectInstance(effect, 0 + increment, potency))
    else
    {
        val duration = min(instance.duration + increment, max)
        addStatusEffect(StatusEffectInstance(effect, duration, potency))
    }
}