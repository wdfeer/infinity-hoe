package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.extension.stackStatusDuration
import org.wdfeer.infinity_hoe.util.TickDurationHelper.minutesToTicks
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

object Fleeting : UsableHarvestChargeEnchantment(Rarity.RARE) {
    private val DURATION = secondsToTicks(30)

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        player.stackStatusDuration(StatusEffects.SPEED, 0, minutesToTicks(120), DURATION)

        return true
    }

    override fun getTooltipColor(): Formatting = Formatting.BLUE

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 15
    override fun chargeToString(charge: Int): String = "%.1f".format(charge / 10f)

    override fun getPowerRange(level: Int): IntRange = 20..60

    override fun getPath(): String = "fleeting"
}