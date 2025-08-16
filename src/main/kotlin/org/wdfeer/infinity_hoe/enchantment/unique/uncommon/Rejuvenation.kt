package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.extension.stackStatusPotency
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.extension.ifElse
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.jvm.optionals.getOrNull
import kotlin.random.Random

object Rejuvenation : HoeEnchantment, HarvestListener {
    private val DURATION: Int = secondsToTicks(2)
    private const val MAX_ANIMAL_DISTANCE: Int = 20

    private const val HOE_DAMAGE_CHANCE_DIVIDER = 80

    private const val CONTAGION_IMMUNITY_CHANCE_DIVIDER = 200
    private const val CONTAGION_CURE_CHANCE_DIVIDER = 80

    override fun getPath(): String = "rejuvenation"

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
            val infectedStatus = Registries.STATUS_EFFECT.getEntry(Identifier.of("contagion", "infection")).getOrNull()
                ?: run{
                    InfinityHoe.logger.error("Contagion infection status effect not found!")
                    return
                }

            val infected = entity.hasStatusEffect(infectedStatus)

            val chanceDivider = if (infected) CONTAGION_CURE_CHANCE_DIVIDER else CONTAGION_IMMUNITY_CHANCE_DIVIDER

            if (!Random.roll(chanceDivider)) return

            val immunity = Registries.STATUS_EFFECT.getEntry(Identifier.of("contagion", "immunity")).getOrNull() ?: return
            entity.addStatusEffect(StatusEffectInstance(immunity, secondsToTicks(infected.ifElse(10, 240))))
        }
    }
}