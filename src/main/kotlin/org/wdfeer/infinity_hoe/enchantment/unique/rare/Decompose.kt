package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.MendingEnchantment
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.extension.enchantmentMap
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.randomRound
import kotlin.math.max
import kotlin.math.pow

class Decompose : HoeEnchantment(Rarity.RARE), HoldTicker {
    companion object {
        private const val INTERVAL: Int = 5
        private const val DISTANCE: Int = 4
    }

    override fun getPath(): String = "decompose"
    override fun getPowerRange(level: Int): IntRange = 16..50
    override fun canAccept(other: Enchantment?): Boolean = other !is MendingEnchantment


    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % INTERVAL == 0L

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val compostables = world.iterateEntities()
            .filterIsInstance<ItemEntity>()
            .filter { it.distanceTo(player) < DISTANCE }
            .map { it.stack }
            .associateWith { CompostingChanceRegistry.INSTANCE[it.item] }
            .filterValues { it > 0f }

        val stack = compostables.keys.randomOrNull() ?: return
        val power = (compostables[stack]!! + 1).pow(3)

        if (repair(hoe, power) || recharge(hoe, power))
            stack.decrement(1)
    }

    private fun repair(hoe: ItemStack, power: Float): Boolean {
        if (hoe.damage <= 0) return false

        hoe.damage -= power.randomRound()
        return true
    }
    private fun recharge(hoe: ItemStack, power: Float): Boolean = hoe.enchantmentMap.keys
            .filterIsInstance<ChargeEnchantment>()
            .any { it.increment(hoe, power.randomRound()) }

    private fun ChargeEnchantment.increment(hoe: ItemStack, amount: Int = 1): Boolean {
        val charge = getCharge(hoe)
        val maxCharge = getMaxCharge(hoe.getEnchantmentLevel(this))
        if (charge >= maxCharge) return false

        setCharge(hoe, minOf(maxCharge, charge + amount))
        return true
    }
}