package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader.demeterAegis
import org.wdfeer.infinity_hoe.enchantment.demeter.DemeterEnchantment
import org.wdfeer.infinity_hoe.extension.hasEnchantment

class DemeterAegis : DemeterEnchantment() {
    override fun getPath(): String = "demeter_aegis"

    private fun getNullifications(charge: Int): Float = charge / 200f

    override fun getMaxCharge(level: Int): Int = 400
    override fun getChargeDecrement(): Int = 200
    override fun chargeToString(charge: Int): String = "%.2f".format(getNullifications(charge))

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other !is MysticBlade

    companion object {
        fun preDamage(player: ServerPlayerEntity) {
            val hoe = player.handItems.find { it.item is HoeItem &&
                        it.hasEnchantment(demeterAegis) &&
                        demeterAegis.getCharge(it) >= demeterAegis.getChargeDecrement() } ?: return

            if (player.statusEffects.any { it.effectType == StatusEffects.RESISTANCE && it.amplifier == 255 }) return

            player.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, 20, 255))

            demeterAegis.setCharge(hoe, demeterAegis.getCharge(hoe) - demeterAegis.getChargeDecrement())
        }
    }
}