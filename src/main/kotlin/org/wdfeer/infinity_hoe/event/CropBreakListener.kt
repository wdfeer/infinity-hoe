package org.wdfeer.infinity_hoe.event

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
import org.wdfeer.infinity_hoe.enchantment.ModEnchantments
import org.wdfeer.infinity_hoe.util.hasEnchantment

object CropBreakListener {
    fun initialize() {
        PlayerBlockBreakEvents.AFTER.register {world, player, pos, state, _ -> onBlockBreak(world, player, pos, state)}
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
        val hoe: ItemStack = player.handItems.first()
        if (hoe.item !is HoeItem) return

        val crop = state.block as CropBlock
        val mature = crop.getAge(state) >= crop.maxAge

        ModEnchantments.enchantments.forEach {
            if (it != cause && hoe.hasEnchantment(it))
                it.onCropBroken(world, player, hoe, pos, state, mature)
        }
    }
}