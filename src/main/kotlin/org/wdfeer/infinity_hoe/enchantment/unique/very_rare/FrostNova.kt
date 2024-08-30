package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Style
import org.wdfeer.infinity_hoe.enchantment.parent.NovaEnchantment
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

class FrostNova : NovaEnchantment() {
    override fun getPath(): String = "frost_nova"

    override fun affect(player: ServerPlayerEntity, target: LivingEntity, powerMult: Float) {
        target.damage(DamageTypes.INDIRECT_MAGIC, 4f * powerMult, player)

        target.frozenTicks = secondsToTicks(8)

        target.addStatusEffect(
            StatusEffectInstance(
                StatusEffects.SLOWNESS,
                secondsToTicks(10) * powerMult.toInt(),
                powerMult.toInt() * 2 - 1
            )
        )
    }

    override fun getTooltipStyle(): Style = Style.EMPTY.withColor(0x00dfdf)
}