package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Style
import org.wdfeer.infinity_hoe.enchantment.parent.NovaEnchantment
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.util.TickDurationHelper

object PoisonNova : NovaEnchantment() {
    override fun getPath(): String = "poison_nova"

    override fun affect(player: ServerPlayerEntity, target: LivingEntity, powerMult: Float) {
        target.damage(DamageTypes.INDIRECT_MAGIC, 5f * powerMult, player)

        target.addStatusEffect(
            StatusEffectInstance(
                StatusEffects.POISON,
                TickDurationHelper.secondsToTicks(10) * powerMult.toInt(),
                powerMult.toInt() - 1
            )
        )
    }

    override fun getTooltipStyle(): Style = Style.EMPTY.withColor(0x4db560)
}