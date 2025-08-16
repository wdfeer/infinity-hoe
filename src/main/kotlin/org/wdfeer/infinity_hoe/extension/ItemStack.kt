package org.wdfeer.infinity_hoe.extension

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ItemEnchantmentsComponent
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import kotlin.jvm.optionals.getOrNull

val ItemStack.enchantmentMap: Map<RegistryKey<Enchantment>, Int>
    get() = this.enchantments.enchantments.associate { entry: RegistryEntry<Enchantment> ->
        entry.key.get() to enchantments.getLevel(entry)
    }

fun ItemStack.hasEnchantment(enchantment: RegistryKey<Enchantment>): Boolean = enchantments.enchantments.any { it.key.getOrNull() == enchantment.value }
fun ItemStack.hasEnchantment(enchantment: HoeEnchantment): Boolean = enchantments.enchantments.any { it.key.getOrNull() == enchantment.registryKey }

fun ItemStack.getEnchantmentLevel(enchantment: RegistryKey<Enchantment>): Int = enchantmentMap[enchantment] ?: -1
fun ItemStack.getEnchantmentLevel(enchantment: HoeEnchantment): Int = enchantmentMap[enchantment.registryKey] ?: -1

fun ItemStack.addEnchantment(enchantment: RegistryKey<Enchantment>, level: Int) =
    addEnchantment(enchantment.getEntry(), level)

fun ItemStack.removeEnchantment(enchantment: RegistryKey<Enchantment>) =
    // TODO: maybe use DataComponentTypes.STORED_ENCHANTMENTS instead
    set(DataComponentTypes.ENCHANTMENTS, get(DataComponentTypes.ENCHANTMENTS).also {
        it?.enchantments?.remove(enchantment.getEntry())
    })

fun ItemStack.damage(player: ServerPlayerEntity, amount: Int = 1) =
    this.damage(amount, player, EquipmentSlot.MAINHAND)

fun ItemStack.damage(amount: Int = 1) =
    this.damage(amount, null, null)