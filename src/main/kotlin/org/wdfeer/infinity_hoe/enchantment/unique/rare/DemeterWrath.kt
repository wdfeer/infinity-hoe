package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.emitter.HoeHit
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage

class DemeterWrath : DemeterEnchantment(), PreAttackListener {
    override fun getPath(): String = "demeter_wrath"

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        target.damage(DamageTypes.MAGIC, DAMAGE, player)
        target.hurtTime = 0

        setCharge(hoe, charge - getChargeDecrement())

        HoeHit.postHit(hoe, target, player, this)
    }

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 500
    override fun getChargeDecrement(): Int = 5
    override fun chargeToString(charge: Int): String = "%.1f".format(charge.toFloat() / getChargeDecrement())

    override fun getTooltipArgs(hoe: ItemStack): List<String> =
        super.getTooltipArgs(hoe).toMutableList().apply {
            addFirst("%.1f".format(DAMAGE))
        }

    companion object {
        private const val DAMAGE = 5f
    }
}