package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.random.Random

object DemeterGrace : DemeterEnchantment(), PlayerDamageTaken {
    override fun getPath(): String = "demeter_grace"

    private fun getProcs(charge: Int): Float = charge / 50f

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 32
    override fun getChargeDecrement(): Int = 50
    override fun chargeToString(charge: Int): String = "%.2f".format(getProcs(charge))

    override fun postDamageTaken(player: ServerPlayerEntity, amount: Float) {
        if (player.maxHealth - player.health < 1f) return
        if (!Random.roll(amount * 0.4f + 0.2f)) return

        val hoe = player.handItems.find { getCharge(it) >= getChargeDecrement() } ?: return

        player.heal(amount / 4f + 1f)

        setCharge(hoe, getCharge(hoe) - getChargeDecrement())
    }
}