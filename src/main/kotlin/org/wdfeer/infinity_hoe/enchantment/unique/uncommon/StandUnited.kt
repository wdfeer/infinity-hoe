package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.rare.Equinox
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.*
import org.wdfeer.infinity_hoe.util.MathHelper
import org.wdfeer.infinity_hoe.util.TickDurationHelper.minutesToTicks
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import kotlin.random.Random

object StandUnited : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    private const val SHARE_DISTANCE: Int = 20
    private fun getMaxDuration(level: Int): Int = minutesToTicks(10 + level * 10)
    private fun getDurationDelta(level: Int): Int = secondsToTicks(2 + level * 4)

    override fun getPowerRange(level: Int): IntRange = (10..20).incrementBounds(level * 7)

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
        return MathHelper.triangleCurve(duration, highPoint, max) / 7f
    }

    override fun canAccept(other: Enchantment?): Boolean = other !is Equinox && other !is Rejuvenation
}