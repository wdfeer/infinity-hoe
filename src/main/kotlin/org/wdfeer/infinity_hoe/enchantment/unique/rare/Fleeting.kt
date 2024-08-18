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
        private val DURATION = secondsToTicks(10)
        private const val AMPLIFIER = 1
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        player.addStatusEffect(StatusEffectInstance(StatusEffects.SPEED, DURATION, AMPLIFIER))

        return true
    }

    override fun getTooltipFormatting(): Formatting = Formatting.BLUE

    override fun getPowerRange(level: Int): IntRange = 20..60

    override fun getPath(): String = "fleeting"
}