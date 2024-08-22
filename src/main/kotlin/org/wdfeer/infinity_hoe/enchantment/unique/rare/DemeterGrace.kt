package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken

class DemeterGrace : DemeterEnchantment(), PlayerDamageTaken {
    override fun getPath(): String = "demeter_grace"

    private fun getProcs(charge: Int): Float = charge / 200f

    override fun getMaxCharge(level: Int): Int = 400
    override fun getChargeDecrement(): Int = 200
    override fun chargeToString(charge: Int): String = "%.2f".format(getProcs(charge))

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other !is MysticBlade

    override fun postDamageTaken(player: ServerPlayerEntity, amount: Float) {
        val hoe = player.handItems.find { getCharge(it) >= getChargeDecrement() } ?: return

        // TODO: Implement healing

        setCharge(hoe, getCharge(hoe) - getChargeDecrement())
    }
}