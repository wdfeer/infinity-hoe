package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import org.wdfeer.infinity_hoe.enchantment.demeter.DemeterEnchantment

class DemeterAegis : DemeterEnchantment() {
    override fun getPath(): String = "demeter_wrath"

    // TODO: Implement Protection

    private fun getDamageReduction(charge: Int): Float = charge / (charge + 100f)

    override fun getMaxCharge(level: Int): Int = 10000
    override fun getChargeDecrement(): Int = 1 // Only affects the tooltip color
    override fun chargeToString(charge: Int): String = "%.1f".format(getDamageReduction(charge))

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other !is MysticBlade
}