package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack

abstract class HoeEnchantment(rarity: Rarity) : Enchantment(
    rarity,
    EnchantmentTarget.DIGGER,
    arrayOf(EquipmentSlot.MAINHAND)
), Identifiable {
    open val maxLvl: Int get() = 1
    abstract fun getPowerRange(level: Int): IntRange


    final override fun getMaxLevel(): Int = maxLvl
    final override fun getMinPower(level: Int): Int = getPowerRange(level).first
    final override fun getMaxPower(level: Int): Int = getPowerRange(level).last


    override fun isAcceptableItem(stack: ItemStack?): Boolean {
        return stack?.item is HoeItem
    }

    override fun isAvailableForRandomSelection(): Boolean = isAvailableRandomly


    /* Workaround due to not being able to make a custom EnchantmentTarget.
   Enchanting Tables include hoe enchantments on all diggers even if
   isAcceptableItem(stack: ItemStack?) is overwritten properly.
   Should be fixed since MC 1.20.5
   */
    companion object {
        private var isAvailableRandomly: Boolean = true

        fun setStackBeingProcessed(stack: ItemStack?) { // Called from mixin
            isAvailableRandomly = stack == null || stack.item is HoeItem
        }
    }
}