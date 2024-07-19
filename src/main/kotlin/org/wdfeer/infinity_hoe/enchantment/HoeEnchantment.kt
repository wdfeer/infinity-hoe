package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack

abstract class HoeEnchantment(rarity: Rarity) : Enchantment(rarity, EnchantmentTarget.DIGGER, arrayOf(EquipmentSlot.MAINHAND)), Identifiable {
    override fun isAcceptableItem(stack: ItemStack?): Boolean {
        return stack?.item is HoeItem
    }
}