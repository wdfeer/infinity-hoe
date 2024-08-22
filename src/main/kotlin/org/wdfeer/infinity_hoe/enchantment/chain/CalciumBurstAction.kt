package org.wdfeer.infinity_hoe.enchantment.chain

import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.item.BoneMealItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.inventoryStacks

class CalciumBurstAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos
) : ChainAction<Any?>(world, hoe, player, origin) {
    override fun processBlock(pos: BlockPos) {
        val boneMealStacks = player.inventoryStacks.filter { !it.isEmpty && it.item == Items.BONE_MEAL }

        if (boneMealStacks.isEmpty()) return

        repeat(hoe.getEnchantmentLevel(getEnchantment())) {
            val stack = boneMealStacks.find { !it.isEmpty } ?: return
            BoneMealItem.useOnFertilizable(stack, world, pos)
        }
    }

    override fun canDamageHoe(): Boolean = false

    override fun isValidBlockState(state: BlockState): Boolean = state.block is CropBlock

    override fun getEnchantment(): HoeEnchantment = EnchantmentLoader.calciumBurst
}