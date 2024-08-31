package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Style
import org.wdfeer.infinity_hoe.enchantment.parent.NovaEnchantment

class HealingNova : NovaEnchantment() {
    override fun getPath(): String = "healing_nova"

    override fun canAffect(player: ServerPlayerEntity, target: LivingEntity): Boolean =
        target.isAlive

    override fun affect(player: ServerPlayerEntity, target: LivingEntity, powerMult: Float) {
        target.addStatusEffect(
            StatusEffectInstance(
                StatusEffects.INSTANT_HEALTH,
                1,
                powerMult.toInt() - 1 * 3
            )
        )
    }

    override fun getTooltipStyle(): Style = Style.EMPTY.withColor(0xffff33)
}