package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.catalyze.CropCatalyzer
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.event.listener.OnHitListener
import org.wdfeer.infinity_hoe.util.TickDurationHelper.minutesToTicks
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

class Equinox : HoeEnchantment(Rarity.RARE), OnHitListener, HarvestListener {
    companion object {
        private val MAX_DURATION: Int = minutesToTicks(15)
        private val DURATION_INCREASE: Int = secondsToTicks(5)
    }

    override fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        if (attacker !is ServerPlayerEntity || attacker.world !is ServerWorld) return

        CropCatalyzer.trigger(attacker.world as ServerWorld, attacker, 1, hoe)
    }

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!mature) return

        val strength = player.getStatusEffect(StatusEffects.STRENGTH)

        if (strength == null)
            player.addStatusEffect(StatusEffectInstance(StatusEffects.STRENGTH, DURATION_INCREASE), player)
        else if (strength.amplifier == 0 && strength.duration < MAX_DURATION)
            player.addStatusEffect(StatusEffectInstance(StatusEffects.STRENGTH, strength.duration + DURATION_INCREASE), player)
    }


    override fun getPath(): String = "equinox"
    override fun getPowerRange(level: Int): IntRange = 22..50


    override fun canAccept(other: Enchantment?): Boolean = other !is GrowthAcceleration && other !is SoulSiphon && other !is StandUnited
}