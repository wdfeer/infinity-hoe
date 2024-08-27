package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.UnbreakingEnchantment
import net.minecraft.entity.ItemEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.HoeItem
import net.minecraft.item.Item
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.common.AutoSeed
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.extension.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.randomRound
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
                plant(world, hoeEntity.blockPos)

            hoeEntity.setNeverDespawn()
        }

        private fun isMatureCrop(world: ServerWorld, pos: BlockPos): Boolean {
            val state = world.getBlockState(pos)
            val crop = state.block as? CropBlock ?: return false
            return crop.isMature(state)
        }

        private fun plant(world: ServerWorld, origin: BlockPos) {
            val stacks = world.iterateEntities()
                .filterIsInstance<ItemEntity>()
                .filter { it.pos.distanceTo(origin.toCenterPos()) < SEED_COLLECT_RANGE }
                .map { it.stack }
                .filter { getCropBlock(it.item) != null }

            val positions = origin.getAdjacentHorizontally(HARVEST_RANGE)
                .filter { world.isAir(it) && world.getBlockState(it.down()).block == Blocks.FARMLAND }

            for (pos in positions) {
                val stack = stacks.find { !it.isEmpty } ?: continue

                world.setBlockState(pos, getCropBlock(stack.item)?.defaultState ?: continue)
                stack.decrement(1)
            }
        }

        private fun getCropBlock(item: Item): CropBlock? = (item as? BlockItem)?.block as? CropBlock
    }
}