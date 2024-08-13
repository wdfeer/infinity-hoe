package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.TickDurationHelper.minutesToTicks
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class StandUnited : HoeEnchantment(Rarity.RARE), HarvestListener {
    private companion object {
        const val SHARE_DISTANCE: Int = 20
        fun getMaxDuration(level: Int): Int = minutesToTicks(10 + level * 10)
        private fun getDurationDelta(level: Int): Int = secondsToTicks(2 + level * 4)
    }

    override fun getPowerRange(level: Int): IntRange = 10 + level * 5..25 + level * 5

    override val maxLvl: Int
        get() = 3

    override fun getPath(): String = "stand_united"

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!mature) return

        world.players
            .filter { it.pos.distanceTo(pos.toCenterPos()) <= SHARE_DISTANCE }
            .forEach { processPlayer(it, hoe.getEnchantmentLevel(this)) }
    }

    private fun processPlayer(player: ServerPlayerEntity, level: Int) {
        val resist = player.getStatusEffect(StatusEffects.RESISTANCE)
        if (resist == null)
            player.addStatusEffect(StatusEffectInstance(
                StatusEffects.RESISTANCE,
                getDurationDelta(level)
            ))
        else if (resist.amplifier == 0 && resist.duration < getMaxDuration(level))
            player.addStatusEffect(StatusEffectInstance(
                StatusEffects.RESISTANCE,
                resist.duration + getDurationDelta(level)
            ))

    }
}