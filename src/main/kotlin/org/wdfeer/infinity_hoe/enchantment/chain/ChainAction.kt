package org.wdfeer.infinity_hoe.enchantment.chain

import com.google.common.math.IntMath.pow
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

abstract class ChainAction(
    val world: ServerWorld,
    val hoe: ItemStack,
    val player: ServerPlayerEntity
) {
    abstract fun processBlock(pos: BlockPos)
    abstract fun getRequiredBlock(): Block

    /**
     * Returns the enchantment determining the initial power
     */
    abstract fun getEnchantment(): HoeEnchantment

    protected open fun canDamageHoe(): Boolean = true
    protected open fun airAboveRequired(): Boolean = true


    private fun getInitialPower(level: Int): Int = pow(4, level + 1)
    private fun getPower(): Int = getInitialPower(hoe.getEnchantmentLevel(getEnchantment()))
    private var power = getPower()


    protected open fun isValidBlockState(state: BlockState): Boolean = state.block == getRequiredBlock()
    private fun isValidBlock(origin: BlockPos, alreadyIncluded: (BlockPos) -> Boolean, pos: BlockPos): Boolean {
        val notDuplicate = pos != origin && !alreadyIncluded(pos)
        val validBlockType = isValidBlockState(world.getBlockState(pos))
        val airCondition = !airAboveRequired() || world.getBlockState(pos.up()).isAir

        return notDuplicate && validBlockType && airCondition
    }
    private fun getNext(origin: BlockPos, alreadyIncluded: (BlockPos) -> Boolean): List<BlockPos> =
        origin.getAdjacentHorizontally(1).filter { isValidBlock(origin, alreadyIncluded, it) }

    private lateinit var blocks: MutableList<BlockPos>
    // Has to be called in children
    protected fun initBlocks(origin: BlockPos) { blocks = getNext(origin) { false }.toMutableList() }

    fun tick() {
        val newBlocks: MutableList<BlockPos> = mutableListOf()

        fun getNext(origin: BlockPos): List<BlockPos> = getNext(origin) { blocks.contains(it) || newBlocks.contains(it) }

        for (pos in blocks) {
            if (!isActive()) return

            processBlock(pos)
            if (canDamageHoe()) hoe.damage(player)
            power--
            newBlocks.addAll(getNext(pos))
        }

        blocks = newBlocks
    }

    fun isActive(): Boolean = blocks.isNotEmpty() && !hoe.isEmpty && power >= 0
}