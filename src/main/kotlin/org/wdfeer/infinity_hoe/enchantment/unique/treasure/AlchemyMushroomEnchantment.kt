package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.BrewingStandBlockEntity
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

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 12 == 0

    private const val TICK_COUNT = 25
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        if (player.inventoryStacks.none { it.hasEnchantment(this) }) return

        val brewingStandPositions = player.blockPos.getAdjacent(5).filter {
            world.getBlockState(it).block == Blocks.BREWING_STAND
        }

        val pos = brewingStandPositions.random()
        val state = world.getBlockState(pos)

        val blockEntity = world.getBlockEntity(pos) as? BrewingStandBlockEntity ?: return
        val ticker = state.getBlockEntityTicker(world, BlockEntityType.BREWING_STAND) ?: return

        repeat(TICK_COUNT) {
            ticker.tick(world, pos, state, blockEntity)
        }
    }
}