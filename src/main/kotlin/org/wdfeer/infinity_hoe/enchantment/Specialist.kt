package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.common.HoeEnchantment
import org.wdfeer.infinity_hoe.util.roll
import kotlin.random.Random

class Specialist(private val path: String, private val crop: Block) : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = path

    override fun getMinPower(level: Int): Int = 10

    override fun getMaxPower(level: Int): Int = 50

    override fun canAccept(other: Enchantment?): Boolean = other !is Specialist

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) {
        if (mature && state.block == crop && Random.roll(DOUBLE_HARVEST_CHANCE)) {
            getDroppedItemEntities(world, state, pos).forEach { world.spawnEntity(it) }
        }
    }

    private fun getDroppedItemEntities(
        world: ServerWorld,
        state: BlockState,
        pos: BlockPos,
    ): List<ItemEntity> {
        return Block.getDroppedStacks(state, world, pos, null).map { drop ->
            ItemEntity(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), drop)
        }
    }


    companion object {
        private const val DOUBLE_HARVEST_CHANCE = 0.2f

        val enchantments: List<HoeEnchantment> = listOf(
            Specialist("beetroot_specialist", Blocks.BEETROOTS),
            Specialist("carrot_specialist", Blocks.CARROTS),
            Specialist("potato_specialist", Blocks.POTATOES),
            Specialist("wheat_specialist", Blocks.WHEAT)
        )
    }
}