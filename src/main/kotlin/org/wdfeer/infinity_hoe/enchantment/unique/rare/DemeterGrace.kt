package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader.demeterAegis
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.extension.hasEnchantment

class DemeterGrace : DemeterEnchantment() {
    override fun getPath(): String = "demeter_grace"

    private fun getProcs(charge: Int): Float = charge / 200f

    override fun getMaxCharge(level: Int): Int = 400
    override fun getChargeDecrement(): Int = 200
    override fun chargeToString(charge: Int): String = "%.2f".format(getProcs(charge))

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other !is MysticBlade

    // TODO: create a onDamageTaken listener for DemeterGrace and DemeterAegis
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