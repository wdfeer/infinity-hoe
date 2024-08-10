package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import kotlin.math.min

class Rejuvenation : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    companion object {
        private const val DURATION: Int = 2 * 20
    }

    override fun getPath(): String = "rejuvenation"
    override fun getPowerRange(level: Int): IntRange = 10..40

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) {
            val regen = min(player.getStatusEffect(StatusEffects.REGENERATION)?.amplifier ?: -1, 9)

            player.removeStatusEffect(StatusEffects.REGENERATION)
            player.addStatusEffect(StatusEffectInstance(StatusEffects.REGENERATION, DURATION, regen + 1))
        }
    }
}