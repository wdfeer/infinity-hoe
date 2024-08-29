package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.letBounds

class DemeterWrath : DemeterEnchantment(), PreAttackListener {
    override fun getPath(): String = "demeter_wrath"

    override val maxLvl: Int
        get() = 2

    override fun getPowerRange(level: Int): IntRange =
        super.getPowerRange(level).letBounds { it + level * 2 }

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        target.hurtTime = 0
        target.damage(DamageTypes.MAGIC, getDamage(hoe), player)
        setCharge(hoe, charge - getChargeDecrement())
    }

    override fun getMaxCharge(level: Int): Int = 2500 * level
    override fun getChargeDecrement(): Int = 5
    override fun chargeToString(charge: Int): String = "%.1f".format(charge.toFloat() / getChargeDecrement())

    override fun getTooltipArgs(hoe: ItemStack): List<String> =
        buildList {
            add("%.1f".format(getDamage(hoe)))
            addAll(super.getTooltipArgs(hoe))
        }

    private fun getDamage(hoe: ItemStack): Float = 1f + hoe.getEnchantmentLevel(this) * 1.5f
}