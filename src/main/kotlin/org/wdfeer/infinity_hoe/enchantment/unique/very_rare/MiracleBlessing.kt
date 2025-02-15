package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.rare.AnimalBlessing
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.extension.*
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import kotlin.random.Random

object MiracleBlessing : HoeEnchantment(Rarity.VERY_RARE), HoldTicker {
    override fun getPath(): String = "miracle_blessing"

    override val maxLvl: Int
        get() = 3

    override fun getPowerRange(level: Int): IntRange = (17..23).incrementBounds(level * 4)

    override fun canIteratePlayers(world: ServerWorld) = world.time % secondsToTicks(7) == 0L
    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val level = hoe.getEnchantmentLevel(this)

        effects.take(level + 1).forEach { if (Random.roll(7)) it(player) }
    }

    private val effects: List<(ServerPlayerEntity) -> Unit> = listOf({
        it.addStatusEffect(StatusEffectInstance(StatusEffects.ABSORPTION, secondsToTicks(7)))
        if (Random.roll(7)) it.heal(2.5f)
        if (Random.roll(7)) it.hungerManager.exhaustion = 0f
    }, {
        it.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, secondsToTicks(7)))
        if (Random.roll(5)) it.heal(2f)
        if (Random.roll(7)) it.hungerManager.saturationLevel += 1f
    }, {
        it.addStatusEffect(StatusEffectInstance(StatusEffects.HASTE, secondsToTicks(7)))
        if (Random.roll(3)) it.heal(1.75f)
        if (Random.roll(7)) it.hungerManager.add(1, 1f)
    })

    override fun canAccept(other: Enchantment?): Boolean =
        super.canAccept(other) && other !is GrowthAcceleration && other !is AnimalBlessing
}