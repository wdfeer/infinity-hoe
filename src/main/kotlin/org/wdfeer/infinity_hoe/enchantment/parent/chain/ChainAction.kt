package org.wdfeer.infinity_hoe.enchantment.parent.chain

import com.google.common.math.IntMath.pow
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.extension.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.randomRound

abstract class ChainAction(
    val world: ServerWorld,
    val hoe: ItemStack,
    val player: ServerPlayerEntity,
    origin: BlockPos
) {
    abstract fun processBlock(pos: BlockPos)

    // One of these two has to be overwritten
    open fun getRequiredBlock(): Block? = null
    protected open fun isValidBlockState(state: BlockState): Boolean = state.block == getRequiredBlock()


    /**
     * Returns the enchantment determining the initial power
     */
    abstract fun getEnchantment(): HoeEnchantment

    protected open val hoeDamage: Float = 1f

    private fun getInitialPower(level: Int): Int = pow(4, level + 1)
    private fun getPower(): Int = getInitialPower(hoe.getEnchantmentLevel(getEnchantment()))
    private var power = getPower()

    protected open val checkForAirAboveBlock: Boolean = true

    private fun isValidBlock(origin: BlockPos, alreadyIncluded: (BlockPos) -> Boolean, pos: BlockPos): Boolean {
        val notDuplicate = pos != origin && !alreadyIncluded(pos)
        val validBlockType = isValidBlockState(world.getBlockState(pos))
        val airCondition = !checkForAirAboveBlock || world.getBlockState(pos.up()).isAir

        return notDuplicate && validBlockType && airCondition
    }

    private fun getNext(origin: BlockPos, alreadyIncluded: (BlockPos) -> Boolean): List<BlockPos> =
        origin.getAdjacentHorizontally(1).filter { isValidBlock(origin, alreadyIncluded, it) }

    private var blocks: List<BlockPos> = listOf(origin)

    private val isFirstTick: Boolean get() = power == getPower()

    fun tick() {
        val newBlocks: MutableList<BlockPos> = mutableListOf()

        fun getNext(origin: BlockPos): List<BlockPos> =
            getNext(origin) { blocks.contains(it) || newBlocks.contains(it) }

        if (isFirstTick) blocks = getNext(blocks[0])

        for (pos in blocks) {
            if (!isActive()) return

            processBlock(pos)
            val damageAmount = hoeDamage.randomRound()
            if (damageAmount > 0) hoe.damage(player, damageAmount)
            power--
            newBlocks.addAll(getNext(pos))
        }

        blocks = newBlocks
    }

    fun isActive(): Boolean = blocks.isNotEmpty() && !hoe.isEmpty && power >= 0
}