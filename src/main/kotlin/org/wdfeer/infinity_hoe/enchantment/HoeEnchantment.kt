package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack

abstract class HoeEnchantment(rarity: Rarity) : Enchantment(rarity, EnchantmentTarget.DIGGER, arrayOf(EquipmentSlot.MAINHAND)), Identifiable {
    open val maxLevel: Int get() = 1
    abstract fun getPowerRange(level: Int): IntRange


    final override fun getMaxLevel(): Int = maxLevel
    final override fun getMinPower(level: Int): Int = getPowerRange(level).first
    final override fun getMaxPower(level: Int): Int = getPowerRange(level).last


    override fun isAcceptableItem(stack: ItemStack?): Boolean {
        return stack?.item is HoeItem
    }
}