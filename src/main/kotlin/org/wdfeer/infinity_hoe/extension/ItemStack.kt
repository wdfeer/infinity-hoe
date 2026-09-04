package org.wdfeer.infinity_hoe.extension

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ItemEnchantmentsComponent
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import kotlin.jvm.optionals.getOrNull

val ItemStack.enchantmentMap: Map<RegistryKey<Enchantment>, Int>
    get() = enchantments.enchantments.associate { entry: RegistryEntry<Enchantment> ->
        entry.key.get() to enchantments.getLevel(entry)
    }

fun ItemStack.hasEnchantment(enchantment: RegistryKey<Enchantment>): Boolean =
    enchantments.enchantments.any { it.key.getOrNull() == enchantment.value }

fun ItemStack.hasEnchantment(enchantment: HoeEnchantment): Boolean =
    enchantments.enchantments.any { it.key.getOrNull() == enchantment.registryKey }

fun ItemStack.getEnchantmentLevel(enchantment: RegistryKey<Enchantment>): Int =
    enchantmentMap[enchantment] ?: -1

fun ItemStack.getEnchantmentLevel(enchantment: HoeEnchantment): Int =
    enchantmentMap[enchantment.registryKey] ?: -1

fun ItemStack.addEnchantment(
    world: ServerWorld,
    enchantment: RegistryKey<Enchantment>,
    level: Int
) =
    addEnchantment(enchantment.getEntry(world), level)

fun ItemStack.removeEnchantment(world: ServerWorld, enchantment: RegistryKey<Enchantment>) {
    val builder = ItemEnchantmentsComponent.Builder(get(DataComponentTypes.ENCHANTMENTS))
    builder.remove { it.key.get().value == enchantment.value }
    set(DataComponentTypes.ENCHANTMENTS, builder.build())
}
// TODO: maybe use DataComponentTypes.STORED_ENCHANTMENTS instead

fun ItemStack.damage(player: ServerPlayerEntity, amount: Int = 1) =
    this.damage(amount, player, EquipmentSlot.MAINHAND)

fun ItemStack.damage(world: ServerWorld, amount: Int = 1) =
    this.damage(amount, world, null) {}