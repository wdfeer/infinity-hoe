package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import net.minecraft.block.Blocks
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.getAdjacent
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks

object AlchemyMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE), PlayerTicker {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "alchemy_mushroom"

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 25 == 0
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        if (player.inventoryStacks.none { it.hasEnchantment(this) }) return

        val positions = player.blockPos.getAdjacent(5)

        for (p in positions) {
            if (world.getBlockState(p).block == Blocks.BREWING_STAND) {
                // TODO: tick it
                break
            }
        }
    }
}