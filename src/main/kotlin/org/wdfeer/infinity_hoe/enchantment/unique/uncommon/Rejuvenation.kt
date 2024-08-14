package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.status.stackStatusPotency
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

class Rejuvenation : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    companion object {
        private val DURATION: Int = secondsToTicks(2)
        const val MAX_ANIMAL_DISTANCE: Int = 20
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
        if (!mature) return

        procRegen(player)

        world.iterateEntities()
            .filterIsInstance<AnimalEntity>()
            .filter { it.isAlive && it.pos.distanceTo(pos.toCenterPos()) <= MAX_ANIMAL_DISTANCE }
            .forEach(::procRegen)
    }

    private fun procRegen(entity: LivingEntity) {
        entity.stackStatusPotency(StatusEffects.REGENERATION, DURATION, 9)
    }
}