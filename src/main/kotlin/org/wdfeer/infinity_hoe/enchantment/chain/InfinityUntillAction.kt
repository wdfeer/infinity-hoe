package org.wdfeer.infinity_hoe.enchantment.chain

import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.Untill
import org.wdfeer.infinity_hoe.util.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

data class InfinityUntillAction(
    val world: ServerWorld,
    val hoe: ItemStack,
    val player: ServerPlayerEntity,
    val origin: BlockPos,
) { // TODO: Create abstract class for InfinityTillAction and InfinityUntillAction (and possibly ChainHarvestAction)
    companion object {
        private fun getPower(hoe: ItemStack): Int =
            ((hoe.item.maxDamage - hoe.damage) / 2 + 1) * (hoe.getEnchantmentLevel(Enchantments.UNBREAKING) + 1)

        private fun getNext(world: World, origin: BlockPos, alreadyIncluded: (BlockPos) -> Boolean): List<BlockPos> {
            fun isValidBlock(pos: BlockPos): Boolean =
                !(pos == origin || alreadyIncluded(pos))
                        && world.getBlockState(pos).block == Blocks.FARMLAND
                        && world.getBlockState(pos.up()).isAir

            return origin.getAdjacentHorizontally(1).filter { isValidBlock(it) }
        }
    }

    private var power: Int = getPower(hoe)
    private var blocks: MutableList<BlockPos> = getNext(world, origin) { false }.toMutableList()


    // Dead till-actions get deleted
    fun isDead(): Boolean = !canTick() || blocks.isEmpty()

    private fun canTick(): Boolean {
        return !hoe.isEmpty && power > 0
    }

    fun tick() {
        val newBlocks: MutableList<BlockPos> = mutableListOf()

        fun getNext(origin: BlockPos): List<BlockPos> = getNext(world, origin) { blocks.contains(it) || newBlocks.contains(it) }

        for (pos in blocks) {
            if (!canTick()) return

            Untill.untill(world, player, hoe, pos, false)

            power--

            newBlocks.addAll(getNext(pos))
        }

        blocks = newBlocks
    }
}