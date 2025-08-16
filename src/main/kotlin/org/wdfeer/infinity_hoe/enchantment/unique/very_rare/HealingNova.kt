package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Monster
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Style
import org.wdfeer.infinity_hoe.enchantment.parent.NovaEnchantment
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import kotlin.math.sqrt

object HealingNova : NovaEnchantment() {
    override fun getPath(): String = "healing_nova"

    override fun canAffect(player: ServerPlayerEntity, target: LivingEntity): Boolean =
        target.isAlive && target !is Monster

    override fun getEffectRange(powerMult: Float): Float = BASE_RANGE * sqrt(powerMult)

    override fun affect(player: ServerPlayerEntity, target: LivingEntity, powerMult: Float) {
        target.addStatusEffect(
            StatusEffectInstance(
                StatusEffects.REGENERATION,
                secondsToTicks((65 - 5f * powerMult).toInt()),
                powerMult.toInt() - 1
            ), player
        )

        if (powerMult > 6f) target.addStatusEffect(
            StatusEffectInstance(
                StatusEffects.ABSORPTION,
                secondsToTicks((10 * powerMult).toInt()),
                0
            ), player
        )
    }

    override fun getChargeDecrement(): Int = super.getChargeDecrement() / 4

    override fun getTooltipStyle(): Style = Style.EMPTY.withColor(0xffff33)
}