package org.wdfeer.infinity_hoe.enchantment.infinity

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.HoeListener
import org.wdfeer.infinity_hoe.util.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

data class InfinityTillAction(
    val world: World,
    val hoe: ItemStack,
    val player: ServerPlayerEntity,
    val origin: BlockPos,
    val blockFilter: Block,
    val autoSeedType: Item? = null
) {
    companion object {
        private fun getPower(hoe: ItemStack): Int =
            ((hoe.item.maxDamage - hoe.damage) / 2 + 1) * (hoe.getEnchantmentLevel(Enchantments.UNBREAKING) + 1)

        private fun getNext(world: World, origin: BlockPos, blockFilter: Block, alreadyIncluded: (BlockPos) -> Boolean): List<BlockPos> {
            fun isValidBlock(pos: BlockPos): Boolean =
                !(pos == origin || alreadyIncluded(pos))
                        && world.getBlockState(pos).block == blockFilter
                        && world.getBlockState(pos.up()).isAir

            fun getNeighbors(range: Int): List<BlockPos> = origin.getAdjacentHorizontally(range).filter { isValidBlock(it) }

            return getNeighbors(1)
        }
    }

    private var power: Int = getPower(hoe)
    private var blocks: MutableList<BlockPos> = getNext(world, origin, blockFilter) { false }.toMutableList()


    // Dead till-actions get deleted
    fun isDead(): Boolean = !canTick() || blocks.isEmpty()

    private fun canTick(): Boolean {
        return !hoe.isEmpty && power > 0
    }

    fun tick() {
        val newBlocks: MutableList<BlockPos> = mutableListOf()

        fun getNext(origin: BlockPos): List<BlockPos> = getNext(world, origin, blockFilter) { blocks.contains(it) || newBlocks.contains(it) }

        for (pos in blocks) {
            if (!canTick()) return

            till(pos)

            damageHoe()

            power--

            newBlocks.addAll(getNext(pos))
        }

        blocks = newBlocks
    }

    private fun damageHoe() {
        hoe.damage(1, player) { p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
    }

    private fun till(pos: BlockPos) {
        world.setBlockState(pos, Blocks.FARMLAND.defaultState)

        if (world is ServerWorld)
            HoeListener.onTill(world, player, hoe, pos, this)
    }
}