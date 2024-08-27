package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.block.Block
import net.minecraft.block.CropBlock
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.UnbreakingEnchantment
import net.minecraft.entity.ItemEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.Item
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.AutoSeed
import org.wdfeer.infinity_hoe.extension.*
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

class Automata : HoeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 25..60

    override fun getPath(): String = "automata"

    override fun canAccept(other: Enchantment?): Boolean = other is UnbreakingEnchantment || other is AutoSeed
    companion object {
        private val CHECK_INTERVAL = secondsToTicks(10)
        private const val HARVEST_RANGE = 2
        private const val SEED_COLLECT_RANGE = 3

        fun mixinItemEntityTick(itemEntity: ItemEntity) {
            val serverWorld = itemEntity.world as? ServerWorld ?: return
            if (serverWorld.time % CHECK_INTERVAL != 0L) return

            if (itemEntity.stack.item !is HoeItem) return
            if (!itemEntity.stack.hasEnchantment(EnchantmentLoader.automata)) return

            tick(serverWorld, itemEntity)
        }

        private fun tick(world: ServerWorld, hoeEntity: ItemEntity) {
            val positions = hoeEntity.blockPos.getAdjacentHorizontally(HARVEST_RANGE)
                .filter { isMatureCrop(world, it) }

            positions.forEach { world.breakBlock(it, true) }
            hoeEntity.stack.damage((positions.size / 16f).randomRound())

            if (hoeEntity.stack.hasEnchantment(EnchantmentLoader.autoSeed))
                replant(world, hoeEntity.pos, positions.associateWith {
                    val block = world.getBlockState(it).block
                    Pair(block, block.asItem())
                })

            hoeEntity.setNeverDespawn()
        }

        private fun isMatureCrop(world: ServerWorld, pos: BlockPos): Boolean {
            val state = world.getBlockState(pos)
            val crop = state.block as? CropBlock ?: return false
            return crop.isMature(state)
        }

        private fun replant(world: ServerWorld, origin: Vec3d, positionsWithCrops: Map<BlockPos, Pair<Block, Item>>) {
            // TODO: fix this not working
            val itemEntities = world.iterateEntities()
                .filterIsInstance<ItemEntity>()
                .filter { it.pos.distanceTo(origin) < SEED_COLLECT_RANGE }
                .filter { e -> positionsWithCrops.values.any { e.stack.item == it.second } }

            for ((pos, pair) in positionsWithCrops) {
                val entity = itemEntities.find { it.stack.item == pair.second } ?: continue

                world.setBlockState(pos, pair.first.defaultState)
                entity.stack.decrement(1)
            }
        }
    }
}