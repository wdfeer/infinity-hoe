package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks

object HealMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE), PlayerDamageTaken, PlayerTicker {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "healing_mushroom"

    override fun preDamageTaken(player: ServerPlayerEntity, amount: Float) = player.heal(
        when {
            player.health < 2f -> 1.5f
            player.health < 5f -> 1f
            amount > 1f -> 0.5f
            else -> 0.1f
        }
    )

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 40 == 0
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        repeat(player.inventoryStacks.count { it.hasEnchantment(this) }) {
            player.heal(0.1f / (1f + it / 4f))
        }
    }
}