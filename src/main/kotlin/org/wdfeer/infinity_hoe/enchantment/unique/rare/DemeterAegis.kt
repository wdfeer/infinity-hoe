package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader.demeterAegis
import org.wdfeer.infinity_hoe.enchantment.demeter.DemeterEnchantment
import org.wdfeer.infinity_hoe.extension.hasEnchantment

class DemeterAegis : DemeterEnchantment() {
    override fun getPath(): String = "demeter_aegis"

    private fun getNullifications(charge: Int): Float = charge / 500f

    override fun getMaxCharge(level: Int): Int = 1000
    override fun getChargeDecrement(): Int = 500
    override fun chargeToString(charge: Int): String = "%.1f".format(getNullifications(charge))

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other !is MysticBlade

    companion object {
        fun preDamage(player: ServerPlayerEntity) {
            if (player.handItems.any { it.item is HoeItem &&
                        it.hasEnchantment(demeterAegis) &&
                        demeterAegis.getCharge(it) >= demeterAegis.getChargeDecrement() }) {
                player.hurtTime = player.maxHurtTime
            }
        }
    }
}