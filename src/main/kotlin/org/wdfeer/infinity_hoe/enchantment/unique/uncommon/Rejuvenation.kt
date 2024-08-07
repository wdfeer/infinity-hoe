package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.getStatusLevel
import kotlin.math.min

class Rejuvenation : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    override fun getPath(): String = "rejuvenation"
    override fun getPowerRange(level: Int): IntRange = 10..40

    private val duration = 2 * 20
    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) {
            val regen = min(player.getStatusLevel(StatusEffects.REGENERATION) ?: -1, 9)

            player.removeStatusEffect(StatusEffects.REGENERATION)
            player.addStatusEffect(StatusEffectInstance(StatusEffects.REGENERATION, duration, regen + 1))
        }
    }
}