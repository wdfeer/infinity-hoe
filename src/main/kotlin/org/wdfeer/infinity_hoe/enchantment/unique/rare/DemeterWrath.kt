package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage
import kotlin.math.log10

class DemeterWrath : DemeterEnchantment(), PreAttackListener {
    override fun getPath(): String = "demeter_wrath"

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        target.hurtTime = 0
        target.damage(DamageTypes.MAGIC, getDamage(charge), player)
        setCharge(hoe, charge - getChargeDecrement())
    }

    private fun getDamage(charge: Int): Float = log10(charge.toFloat() + 1f) * 6

    override fun getMaxCharge(level: Int): Int = 1000
    override fun getChargeDecrement(): Int = 100
    override fun chargeToString(charge: Int): String = "%.2f".format(getDamage(charge))

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other !is MysticBlade
}