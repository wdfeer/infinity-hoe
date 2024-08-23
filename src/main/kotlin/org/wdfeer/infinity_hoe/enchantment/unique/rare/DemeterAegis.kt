package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken

class DemeterAegis : DemeterEnchantment(), PlayerDamageTaken {
    override fun getPath(): String = "demeter_aegis"

    private fun getNullifications(charge: Int): Float = charge / 200f

    override fun getMaxCharge(level: Int): Int = 400
    override fun getChargeDecrement(): Int = 200
    override fun chargeToString(charge: Int): String = "%.2f".format(getNullifications(charge))

    override fun preDamageTaken(player: ServerPlayerEntity, amount: Float) {
        val hoe = player.handItems.find { getCharge(it) >= getChargeDecrement() } ?: return

        if (player.statusEffects.any { it.effectType == StatusEffects.RESISTANCE && it.amplifier == 255 }) return

        player.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, 20, 255))

        setCharge(hoe, getCharge(hoe) - getChargeDecrement())
    }
}