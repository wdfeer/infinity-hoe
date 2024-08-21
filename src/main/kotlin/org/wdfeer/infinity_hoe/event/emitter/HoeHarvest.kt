package org.wdfeer.infinity_hoe.event.emitter

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.demeter.DemeterState
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.hasEnchantment

object HoeHarvest {
    fun initialize() {
        PlayerBlockBreakEvents.AFTER.register {world, player, pos, state, _ -> onBlockBreak(world, player, pos, state) }
    }

    private fun onBlockBreak(
        world: World?,
        player: PlayerEntity?,
        pos: BlockPos?,
        state: BlockState?
    ) {
        if (world is ServerWorld && player is ServerPlayerEntity && pos != null && state?.block is CropBlock) {
            onCropBreak(world, player, pos, state, null)
        }
    }

    fun onCropBreak(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, state: BlockState, cause: Enchantment?) {
        onCropBreak(world, player, pos, state) { it != cause }
    }

    fun onCropBreak(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, state: BlockState, enchantmentFilter: (HoeEnchantment) -> Boolean) {
        val hoe: ItemStack = player.handItems.first()

        val mature = isMature(state)

        if (mature) DemeterState.incrementPlayerHarvestCount(world, player, state.block)

        if (hoe.item !is HoeItem) return

        EnchantmentLoader.enchantments.forEach {
            val listener = it as? HarvestListener ?: return@forEach
            if (hoe.hasEnchantment(it) && enchantmentFilter(it))
                listener.onCropBroken(world, player, hoe, pos, state, mature)
        }
    }

    private fun isMature(state: BlockState): Boolean = (state.block as? CropBlock).let { it?.isMature(state) ?: false }
}