package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.util.TickDurationHelper

class PesticideOverload : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 30..60

    override fun getPath(): String = "pesticide_overload"

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        world.iterateEntities()
            .filterIsInstance<LivingEntity>()
            .filter { it.isAlive && it.distanceTo(player) <= RANGE }
            .forEach { affect(it) }

        return true
    }

    private fun affect(entity: LivingEntity) {
        for (s in listOf(StatusEffects.POISON, StatusEffects.WITHER)) {
            entity.addStatusEffect(StatusEffectInstance(s, TickDurationHelper.secondsToTicks(30), 0))
        }
    }

    override fun getCooldown(): Int = TickDurationHelper.secondsToTicks(10)

    override fun chargeToString(charge: Int): String = "${charge / 10}%"
    override fun getMaxCharge(level: Int): Int = getChargeDecrement()
    override fun getChargeDecrement(): Int = 1000

    override fun getTooltipColor(): Formatting = Formatting.DARK_PURPLE

    companion object {
        private const val RANGE = 50
    }
}