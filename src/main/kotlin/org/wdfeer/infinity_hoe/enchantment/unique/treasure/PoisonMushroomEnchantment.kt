package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.OnHitListener
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks

object PoisonMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE), OnHitListener, PlayerTicker {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "poison_mushroom"

    override fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 20), attacker)
    }

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 20 == 0
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        if (player.inventoryStacks.any { it.hasEnchantment(this) }) {
            val target: LivingEntity = world.iterateEntities()
                .filterIsInstance<LivingEntity>()
                .filter { it is Monster }
                .filter { it.distanceTo(player) < 16 }
                .random()
            target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 2), player)
        }
    }
}