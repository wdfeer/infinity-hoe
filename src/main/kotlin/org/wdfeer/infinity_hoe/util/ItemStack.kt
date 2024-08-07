package org.wdfeer.infinity_hoe.util

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity

fun ItemStack.hasEnchantment(enchantment: Enchantment): Boolean =
    EnchantmentHelper.fromNbt(this.enchantments).contains(enchantment)

fun ItemStack.getEnchantmentLevel(enchantment: Enchantment): Int =
    EnchantmentHelper.fromNbt(this.enchantments)[enchantment] ?: -1

fun ItemStack.damage(player: ServerPlayerEntity, amount: Int = 1) =
    this.damage(amount, player) { p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }