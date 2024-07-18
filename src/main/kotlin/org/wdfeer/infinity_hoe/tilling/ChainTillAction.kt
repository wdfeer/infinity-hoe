package org.wdfeer.infinity_hoe.tilling

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

data class ChainTillAction(
    val world: World,
    val hoe: ItemStack,
    val player: ServerPlayerEntity,
    val origin: BlockPos,
    val blockFilter: Block
) {
    companion object {
        private fun getPower(hoe: ItemStack): Int =
            ((hoe.item.maxDamage - hoe.damage) / 2 + 1) * (hoe.getEnchantmentLevel(Enchantments.UNBREAKING) + 1)

        private fun getNext(world: World, origin: BlockPos, blockFilter: Block, alreadyIncluded: (BlockPos) -> Boolean): List<BlockPos> {
            fun isValidBlock(pos: BlockPos): Boolean =
                !(pos == origin || alreadyIncluded(pos))
                        && world.getBlockState(pos).block == blockFilter
                        && world.getBlockState(pos.up()).isAir

            fun getNeighbors(range: Int): List<BlockPos> {
                val positions: MutableList<BlockPos> = mutableListOf()

                fun addIfValid(pos: BlockPos) = if (isValidBlock(pos)) positions.add(pos) else false

                (-range..range).forEach { x -> (-range..range).forEach { z -> addIfValid(origin.add(x, 0, z)) } }

                return positions
            }

            return getNeighbors(1)
        }
    }

    private var power: Int = getPower(hoe)
    private var blocks: MutableList<BlockPos> = getNext(world, origin, blockFilter) { false }.toMutableList()


    // Dead till actions get deleted
    fun isDead(): Boolean = !canTick() || blocks.isEmpty()

    private fun canTick(): Boolean {
        return !hoe.isEmpty && power > 0
    }

    fun tick() {
        val newBlocks: MutableList<BlockPos> = mutableListOf()

        fun getNext(origin: BlockPos): List<BlockPos> = getNext(world, origin, blockFilter) { blocks.contains(it) || newBlocks.contains(it) }

        for (pos in blocks) {
            if (!canTick()) return

            setFarmland(pos)

            damageHoe()

            power--

            newBlocks.addAll(getNext(pos))
        }

        blocks = newBlocks
    }

    private fun damageHoe() {
        hoe.damage(1, player) { p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
    }

    private fun setFarmland(pos: BlockPos) {
        world.setBlockState(pos, Blocks.FARMLAND.defaultState)
    }
}