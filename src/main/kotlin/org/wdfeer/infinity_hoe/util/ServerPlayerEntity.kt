package org.wdfeer.infinity_hoe.util

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.server.network.ServerPlayerEntity

fun ServerPlayerEntity.getStatusAmplifier(effect: StatusEffect): Int = this.getStatusEffect(effect)?.amplifier ?: -1