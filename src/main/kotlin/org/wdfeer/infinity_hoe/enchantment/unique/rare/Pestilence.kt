package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.SilverfishEntity
import net.minecraft.item.ToolItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.CropCatalyzer
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.math.max
import kotlin.random.Random

// TODO: immunity to silverfish
object Pestilence : HoeEnchantment, PlayerTicker, CropCatalyzer {
    override fun getPath(): String = "pestilence"

    override fun canIteratePlayers(world: ServerWorld) = world.time % 20 == 0L

    private const val DISTANCE = 5f

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val hoe = player.handItems.find { !it.isEmpty && it.hasEnchantment(this) } ?: return

        player.addStatusEffect(StatusEffectInstance(StatusEffects.INFESTED, 40))

        val silverfish = world.iterateEntities().filterIsInstance<SilverfishEntity>().count {
            it.distanceTo(player) < DISTANCE
        }

        if (!Random.roll(getPlayerTickChance(silverfish))) return

        catalyze(world, player, 3, hoe, 0.5f)
    }

    private fun getPlayerTickChance(silverfish: Int): Float = max(0.8f, silverfish / 10f)
}