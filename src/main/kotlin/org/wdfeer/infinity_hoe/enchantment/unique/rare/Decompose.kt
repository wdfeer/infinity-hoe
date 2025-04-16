package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.MendingEnchantment
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AutomataListener
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.extension.enchantmentMap
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.randomRound
import kotlin.math.pow

object Decompose : HoeEnchantment(Rarity.RARE), HoldTicker, AutomataListener {
    private const val INTERVAL: Int = 5
    private const val DISTANCE: Int = 4

    override fun getPath(): String = "decompose"
    override val maxLvl: Int
        get() = 3
    override fun getPowerRange(level: Int): IntRange = (12 + level * 4)..50
    override fun canAccept(other: Enchantment?): Boolean = other !is MendingEnchantment


    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % INTERVAL == 0L

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) =
        decomposeTick(world, player.pos, hoe)

    private fun decomposeTick(world: ServerWorld, origin: Vec3d, hoe: ItemStack) {
        val compostables = world.iterateEntities()
            .filterIsInstance<ItemEntity>()
            .filter { it.pos.distanceTo(origin) < DISTANCE }
            .map { it.stack }
            .associateWith { CompostingChanceRegistry.INSTANCE[it.item] }
            .filterValues { it > 0f }

        val stack = compostables.keys.randomOrNull() ?: return
        val power = (compostables[stack]!! + 1).pow(3)

        repeat(hoe.getEnchantmentLevel(this)) {
            if (repair(hoe, power) || recharge(hoe, power)) stack.decrement(1)
        }
    }

    private fun repair(hoe: ItemStack, power: Float): Boolean {
        if (hoe.damage <= 0) return false

        hoe.damage -= power.randomRound()
        return true
    }

    private fun recharge(hoe: ItemStack, power: Float): Boolean = hoe.enchantmentMap.keys
        .filterIsInstance<ChargeEnchantment>()
        .minus(FungusEnchanter) // you don't want to recharge the timer
        .any { it.increment(hoe, power.randomRound()) }

    private fun ChargeEnchantment.increment(hoe: ItemStack, amount: Int = 1): Boolean {
        val charge = getCharge(hoe)
        val maxCharge = getMaxCharge(hoe.getEnchantmentLevel(this))
        if (charge >= maxCharge) return false

        setCharge(hoe, minOf(maxCharge, charge + amount))
        return true
    }

    override fun postAutomataTick(world: ServerWorld, hoe: ItemEntity) =
        decomposeTick(world, hoe.pos, hoe.stack)
}