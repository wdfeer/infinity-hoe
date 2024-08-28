package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.rare.DemeterWrath
import org.wdfeer.infinity_hoe.enchantment.unique.rare.MysticBlade
import org.wdfeer.infinity_hoe.enchantment.unique.rare.Pesticide
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.util.TickDurationHelper

class PoisonNova : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 30..60

    override fun getPath(): String = "poison_nova"

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val powerMult: Float = getCharge(hoe).toFloat() / getChargeDecrement()

        world.iterateEntities()
            .filterIsInstance<LivingEntity>()
            .filter { it.isAlive && it !is AnimalEntity && it.distanceTo(player) <= BASE_RANGE * powerMult }
            .forEach {
                it.damage(DamageTypes.INDIRECT_MAGIC, 4f * powerMult, player)
                for (s in listOf(StatusEffects.POISON, StatusEffects.WITHER)) {
                    it.addStatusEffect(
                        StatusEffectInstance(
                            s,
                            TickDurationHelper.secondsToTicks(10),
                            powerMult.toInt() - 1
                        )
                    )
                }
            }

        return true
    }

    override fun getCooldown(): Int = TickDurationHelper.secondsToTicks(10)

    override fun chargeToString(charge: Int): String = "${charge * 100 / getChargeDecrement()}%"

    override fun getUsedCharge(charge: Int): Int = charge
    override fun getMaxCharge(level: Int): Int = 3 * getChargeDecrement()
    override fun getChargeDecrement(): Int = 333

    override fun getTooltipColor(): Formatting = Formatting.DARK_PURPLE

    override fun canAccept(other: Enchantment?): Boolean =
        super.canAccept(other) && other !is MysticBlade && other !is Pesticide && other !is DemeterWrath

    companion object {
        private const val BASE_RANGE = 25
    }
}