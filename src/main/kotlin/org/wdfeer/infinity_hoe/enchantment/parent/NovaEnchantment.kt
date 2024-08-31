package org.wdfeer.infinity_hoe.enchantment.parent

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.util.TickDurationHelper

abstract class NovaEnchantment : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    abstract fun affect(player: ServerPlayerEntity, target: LivingEntity, powerMult: Float)

    final override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val powerMult: Float = getCharge(hoe).toFloat() / getChargeDecrement()

        world.iterateEntities()
            .filterIsInstance<LivingEntity>()
            .filter { it.distanceTo(player) <= getEffectRange(powerMult) && canAffect(player, it) }
            .forEach { affect(player, it, powerMult) }

        return true
    }

    protected open fun getEffectRange(powerMult: Float): Float = BASE_RANGE * powerMult
    protected open fun canAffect(player: ServerPlayerEntity, target: LivingEntity): Boolean =
        target.isAlive && target !is AnimalEntity && target != player


    override fun getPowerRange(level: Int): IntRange = 30..60

    override fun getCooldown(): Int = TickDurationHelper.secondsToTicks(4)

    override fun chargeToString(charge: Int): String = "${charge * 100 / getChargeDecrement()}%"

    override fun getUsedCharge(charge: Int): Int = charge
    override fun getMaxCharge(level: Int): Int = 400
    override fun getChargeDecrement(): Int = 200

    companion object {
        const val BASE_RANGE = 25
    }
}