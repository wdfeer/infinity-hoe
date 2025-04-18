package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.block.CropBlock
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.UnbreakingEnchantment
import net.minecraft.entity.ItemEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AutomataListener
import org.wdfeer.infinity_hoe.extension.*
import kotlin.math.roundToInt

object Automata : HoeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 25..60

    override fun getPath(): String = "automata"

    override fun canAccept(other: Enchantment?): Boolean = other is UnbreakingEnchantment || other is AutomataListener

    private const val CHECK_INTERVAL = 8
    const val HARVEST_RANGE = 2
    const val SEED_COLLECT_RANGE = 3

    fun mixinItemEntityTick(itemEntity: ItemEntity) {
        val serverWorld = itemEntity.world as? ServerWorld ?: return
        if (serverWorld.time % CHECK_INTERVAL != 0L) return

        if (itemEntity.stack.item !is HoeItem) return
        if (!itemEntity.stack.hasEnchantment(this)) return

        tick(serverWorld, itemEntity)
    }

    private fun tick(world: ServerWorld, hoeEntity: ItemEntity) {
        val positions = hoeEntity.pos
            .run { BlockPos(x.toInt(), y.roundToInt(), z.toInt()) }
            .getAdjacentHorizontally(HARVEST_RANGE)
            .filter { isMatureCrop(world, it) }

        positions.forEach { world.breakBlock(it, true) }
        hoeEntity.stack.damage((positions.size / 16f).randomRound())

        hoeEntity.setNeverDespawn()

        hoeEntity.stack.enchantmentMap.keys
            .filterIsInstance<AutomataListener>()
            .forEach { it.postAutomataTick(world, hoeEntity) }
    }

    private fun isMatureCrop(world: ServerWorld, pos: BlockPos): Boolean {
        val state = world.getBlockState(pos)
        val crop = state.block as? CropBlock ?: return false
        return crop.isMature(state)
    }
}