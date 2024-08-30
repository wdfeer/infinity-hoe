package org.wdfeer.infinity_hoe.extension

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.random.Random

val ItemStack.enchantmentMap: MutableMap<Enchantment, Int> get() = EnchantmentHelper.fromNbt(this.enchantments)

fun ItemStack.hasEnchantment(enchantment: Enchantment): Boolean = enchantmentMap.contains(enchantment)

fun ItemStack.getEnchantmentLevel(enchantment: Enchantment): Int = enchantmentMap[enchantment] ?: -1

fun ItemStack.damage(player: ServerPlayerEntity, amount: Int = 1) =
    this.damage(amount, player) { p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }

fun ItemStack.damage(amount: Int = 1) =
    this.damage(amount, Random.create(), null)