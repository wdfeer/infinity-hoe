package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.chain.ActionManager
import org.wdfeer.infinity_hoe.enchantment.chain.InfinityUntillAction
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.hasEnchantment
import org.wdfeer.infinity_hoe.util.stacks

class Untill : HoeEnchantment(Rarity.COMMON) {
    override fun getPath(): String = "untill"

    override fun getPowerRange(level: Int): IntRange = 4..40

    companion object {
        fun untill(
            world: World,
            pos: BlockPos,
            player: PlayerEntity?,
            hoe: ItemStack,
            useCallback: CallbackInfoReturnable<ActionResult>
        ) {
            if (world.isClient)
                useCallback.returnValue = ActionResult.SUCCESS
            else if (world is ServerWorld && player is ServerPlayerEntity) {
                untill(world, player, hoe, pos, hoe.hasEnchantment(EnchantmentLoader.infinity))
                useCallback.returnValue = ActionResult.CONSUME
            }
        }

        private val infinityUntillManager = ActionManager<InfinityUntillAction>()

        private fun untill(
            world: ServerWorld,
            player: ServerPlayerEntity,
            hoe: ItemStack,
            pos: BlockPos,
            infinity: Boolean
        ) {
            world.setBlockState(pos, Blocks.DIRT.defaultState)
            hoe.damage(player)

            if (infinity)
                infinityUntillManager.addAction(InfinityUntillAction(world, hoe, player, pos))
        }

        fun canCancelLandUntill(player: ServerPlayerEntity): Boolean = // Called from mixin
            player.inventory.stacks.any {!it.isEmpty && it.item is HoeItem && it.hasEnchantment(EnchantmentLoader.untill)}
    }
}