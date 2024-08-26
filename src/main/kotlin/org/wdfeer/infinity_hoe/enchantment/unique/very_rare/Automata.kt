package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.block.CropBlock
import net.minecraft.entity.ItemEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.extension.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.util.TickDurationHelper.secondsToTicks

class Automata : HoeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 25..60

    override fun getPath(): String = "automata"

    companion object {
        private val CHECK_INTERVAL = secondsToTicks(10)

        fun mixinItemEntityTick(itemEntity: ItemEntity) {
            val serverWorld = itemEntity.world as? ServerWorld ?: return
            if (serverWorld.time % CHECK_INTERVAL != 0L) return

            if (itemEntity.stack.item !is HoeItem) return
            if (!itemEntity.stack.hasEnchantment(EnchantmentLoader.automata)) return

            tick(serverWorld, itemEntity)
        }

        private fun tick(world: ServerWorld, itemEntity: ItemEntity) =
            itemEntity.blockPos.getAdjacentHorizontally(2)
            .filter { isMatureCrop(world, it) }
            .forEach { world.breakBlock(it, true) }

        private fun isMatureCrop(world: ServerWorld, pos: BlockPos): Boolean {
            val state = world.getBlockState(pos)
            val crop = state.block as? CropBlock ?: return false
            return crop.isMature(state)
        }
    }
}