package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.block.CropBlock
import net.minecraft.item.BoneMealItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.getAdjacent
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.inventoryStacks

class CalciumBurst : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    override val maxLvl: Int
        get() = 2
    override fun getPowerRange(level: Int): IntRange = 13..50

    override fun getPath(): String = "calcium_burst"

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) trigger(world, player, pos, hoe.getEnchantmentLevel(this))
    }

    private fun trigger(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, level: Int) {
        val boneMealStacks = player.inventoryStacks.filter { !it.isEmpty && it.item == Items.BONE_MEAL }

        if (boneMealStacks.isEmpty()) return

        pos.getAdjacent(1)
            .filter { world.getBlockState(it).block is CropBlock }
            .forEach { position ->
            repeat(level) {
                val stack = boneMealStacks.find { !it.isEmpty } ?: return@forEach
                BoneMealItem.useOnFertilizable(stack, world, position)
            }
        }
    }
}