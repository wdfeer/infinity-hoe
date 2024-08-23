package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.random.Random

class DemeterAegis : DemeterEnchantment(), PlayerDamageTaken {
    override fun getPath(): String = "demeter_aegis"

    private fun getNullifications(charge: Int): Float = charge / getChargeDecrement().toFloat()

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 6
    override fun getChargeDecrement(): Int = 80
    override fun chargeToString(charge: Int): String = "%.2f".format(getNullifications(charge))

    override fun preDamageTaken(player: ServerPlayerEntity, amount: Float) {
        if (!Random.roll(amount / 3f)) return

        val hoe = player.handItems.find { getCharge(it) >= getChargeDecrement() } ?: return

        if (player.statusEffects.any { it.effectType == StatusEffects.RESISTANCE && it.amplifier == 255 }) return

        player.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, 20, 255))

        setCharge(hoe, getCharge(hoe) - getChargeDecrement())
    }
}