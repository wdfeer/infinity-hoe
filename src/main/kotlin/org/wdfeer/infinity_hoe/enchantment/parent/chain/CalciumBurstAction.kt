package org.wdfeer.infinity_hoe.enchantment.parent.chain

import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.item.BoneMealItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.CalciumBurst
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.inventoryStacks
import org.wdfeer.infinity_hoe.extension.randomRound

class CalciumBurstAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos
) : ChainAction(world, hoe, player, origin) {
    override fun processBlock(pos: BlockPos) {
        val boneMealStacks = player.inventoryStacks.filter { !it.isEmpty && it.item == Items.BONE_MEAL }

        if (boneMealStacks.isEmpty()) return

        val count: Int = (hoe.getEnchantmentLevel(getEnchantment()) * 0.3f + 0.5f).randomRound()
        repeat(count) {
            val stack = boneMealStacks.find { !it.isEmpty } ?: return
            BoneMealItem.useOnFertilizable(stack, world, pos)
        }
    }

    override val hoeDamage: Float
        get() = 0f

    override fun isValidBlockState(state: BlockState): Boolean = (state.block as? CropBlock)?.isMature(state) == false

    override fun getEnchantment(): HoeEnchantment = CalciumBurst
}