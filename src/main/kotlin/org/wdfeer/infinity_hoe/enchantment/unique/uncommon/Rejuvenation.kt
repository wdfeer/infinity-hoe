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
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.roll
import kotlin.random.Random

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

        procRegen(hoe, player, player)

        world.iterateEntities()
            .filterIsInstance<AnimalEntity>()
            .filter { it.isAlive && it.pos.distanceTo(pos.toCenterPos()) <= MAX_ANIMAL_DISTANCE }
            .forEach { procRegen(hoe, player, it) }
    }

    private fun procRegen(hoe: ItemStack, player: ServerPlayerEntity, entity: LivingEntity) {
        entity.stackStatusPotency(StatusEffects.REGENERATION, DURATION * 2, 9)

        if (Random.roll(80)) hoe.damage(player, 1)
    }
}