package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.status.stackStatusPotency
import org.wdfeer.infinity_hoe.enchantment.unique.rare.Equinox
import org.wdfeer.infinity_hoe.enchantment.unique.rare.StandUnited
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.ifElse
import org.wdfeer.infinity_hoe.util.roll
import kotlin.random.Random

class Rejuvenation : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    private companion object {
        val DURATION: Int = secondsToTicks(2)
        const val MAX_ANIMAL_DISTANCE: Int = 20

        const val HOE_DAMAGE_CHANCE_DIVIDER = 80

        const val CONTAGION_IMMUNITY_CHANCE_DIVIDER = 200
        const val CONTAGION_CURE_CHANCE_DIVIDER = 80
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

        if (Random.roll(HOE_DAMAGE_CHANCE_DIVIDER)) hoe.damage(player, 1)

        if (FabricLoader.getInstance().isModLoaded("contagion")) {
            val infected = entity.hasStatusEffect( Registries.STATUS_EFFECT.get(Identifier.of("contagion", "infection")) ?: return)

            val chanceDivider = if (infected) CONTAGION_CURE_CHANCE_DIVIDER else CONTAGION_IMMUNITY_CHANCE_DIVIDER

            if (!Random.roll(chanceDivider)) return

            val immunity = Registries.STATUS_EFFECT.get(Identifier.of("contagion", "immunity")) ?: return
            entity.addStatusEffect(StatusEffectInstance(immunity, secondsToTicks(infected.ifElse(10, 240))))
        }
    }

    override fun canAccept(other: Enchantment?): Boolean = other !is StandUnited && other !is Equinox
}