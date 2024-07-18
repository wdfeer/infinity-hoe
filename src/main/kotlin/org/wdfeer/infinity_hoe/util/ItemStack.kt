package org.wdfeer.infinity_hoe.util

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ItemStack

fun ItemStack.hasEnchantment(enchantment: Enchantment): Boolean =
    EnchantmentHelper.fromNbt(this.enchantments).contains(enchantment)