package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.DoubleHarvestEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener

class Specialist(private val path: String, private val crop: Block) : HoeEnchantment(Rarity.RARE),
    DoubleHarvestEnchantment, HarvestListener {
    override fun getPath(): String = path

    override fun getPowerRange(level: Int): IntRange = 24..60

    override fun canAccept(other: Enchantment?): Boolean = other !is Specialist

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) {
        if (mature && state.block == crop) {
            tryDrop(world, player, state, pos, DOUBLE_HARVEST_CHANCE)
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