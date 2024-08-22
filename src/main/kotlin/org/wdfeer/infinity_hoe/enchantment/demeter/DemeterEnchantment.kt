package org.wdfeer.infinity_hoe.enchantment.demeter

import net.minecraft.enchantment.Enchantment
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.charge.HarvestChargeEnchantment
import org.wdfeer.infinity_hoe.enchantment.charge.UsableHarvestChargeEnchantment

abstract class DemeterEnchantment : HarvestChargeEnchantment(Rarity.RARE) {
    override fun getPowerRange(level: Int): IntRange = 10..50

    override fun getMaxCharge(level: Int): Int = 10000
    override fun getChargeDecrement(): Int = 1 // Only affects the tooltip color

    override fun getTooltipColor(): Formatting = Formatting.GREEN

    override fun canAccept(other: Enchantment?): Boolean = other !is UsableHarvestChargeEnchantment
}