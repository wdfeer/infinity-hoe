package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker

object HealMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE), PlayerDamageTaken, PlayerTicker {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "healing_mushroom"

    override fun preDamageTaken(player: ServerPlayerEntity, amount: Float) {
        if (amount > 1f) player.heal(0.2f)
    }

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 40 == 0
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        player.heal(0.1f)
    }
}