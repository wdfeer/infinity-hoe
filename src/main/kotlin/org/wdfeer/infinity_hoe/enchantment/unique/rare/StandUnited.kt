package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.status.getStatusDuration
import org.wdfeer.infinity_hoe.enchantment.status.stackStatusDuration
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.MathHelper
import org.wdfeer.infinity_hoe.util.TickDurationHelper.minutesToTicks
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import org.wdfeer.infinity_hoe.util.TickDurationHelper.ticksToMinutes
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.roll
import kotlin.random.Random

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
        if (hoe.maxDamage - hoe.damage < 5) return

        val level = hoe.getEnchantmentLevel(this)

        fun processPlayer(player: ServerPlayerEntity) {
            player.stackStatusDuration(StatusEffects.RESISTANCE, 0, getMaxDuration(level), getDurationDelta(level))

            val newDuration = player.getStatusDuration(StatusEffects.RESISTANCE)

            if (Random.roll(getDamageChance(newDuration, getMaxDuration(level)))) hoe.damage(player, 1)
        }

        world.players
            .filter { it.pos.distanceTo(pos.toCenterPos()) <= SHARE_DISTANCE }
            .forEach { processPlayer(it) }
    }

    private fun getDamageChance(duration: Int, max: Int): Float {
        val highPoint = max - minutesToTicks(1)
        return MathHelper.triangleCurve(duration, highPoint, max) / 4f
    }

    override fun canAccept(other: Enchantment?): Boolean = other !is Equinox
}