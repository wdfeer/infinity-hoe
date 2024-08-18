package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

class Fleeting : ChargeEnchantment(Rarity.RARE) {
    companion object {
        private val DURATION = secondsToTicks(5)
        private const val BASE_AMPLIFIER = 1
        private const val LIGHTLY_ARMORED_AMPLIFIER = BASE_AMPLIFIER + 2
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val amplifier = if (player.armor <= 10) LIGHTLY_ARMORED_AMPLIFIER else BASE_AMPLIFIER
        player.addStatusEffect(StatusEffectInstance(StatusEffects.SPEED, DURATION, amplifier))

        return true
    }

    override fun getTooltipFormatting(): Formatting = Formatting.BLUE

    override fun getMaxCharge(level: Int): Int = 10

    override fun getPowerRange(level: Int): IntRange = 20..60

    override fun getPath(): String = "fleeting"
}