package org.wdfeer.infinity_hoe.event

import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.unique.rare.Infinity
import org.wdfeer.infinity_hoe.event.listener.AirUseListener
import org.wdfeer.infinity_hoe.event.listener.TillListener
import org.wdfeer.infinity_hoe.util.hasEnchantment

object HoeUse {
    fun preUseOnBlock( // Called from Mixin
        context: ItemUsageContext
    ) {
        if (context.stack.hasEnchantment(EnchantmentLoader.infinity))
            Infinity.preTill(context.world, context.stack, context.blockPos)
    }

    fun postUseOnBlock(  // Called from Mixin
        context: ItemUsageContext,
        useCallback: CallbackInfoReturnable<ActionResult>
    ) {
        if (context.world is ServerWorld
            && context.player is ServerPlayerEntity
            && useCallback.returnValue == ActionResult.CONSUME)
            onTill(context.world as ServerWorld, context.player as ServerPlayerEntity, context.stack, context.blockPos, null)
        else
            checkUntill(context.world, context.player, context.stack, context.blockPos, useCallback)
    }

    fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos, cause: Enchantment?) {
        EnchantmentLoader.enchantments.forEach {
            val listener = it as? TillListener ?: return@forEach
            if (it != cause && hoe.hasEnchantment(it))
                listener.onTill(world, player, hoe, pos)
        }
    }

    private fun checkUntill(
        world: World,
        player: PlayerEntity?,
        hoe: ItemStack,
        pos: BlockPos,
        useCallback: CallbackInfoReturnable<ActionResult>
    ) {
        val state = world.getBlockState(pos)
        if (state.block == Blocks.FARMLAND && hoe.hasEnchantment(EnchantmentLoader.untill)) {
            EnchantmentLoader.untill.untill(world, pos, player, hoe, useCallback)
        }
    }

    // TODO: Create a mixin to call this
    fun onUseInAir(context: ItemUsageContext, useCallback: CallbackInfoReturnable<ActionResult>) {
        if (context.world is ServerWorld
            && context.player is ServerPlayerEntity
            && context.stack.item is HoeItem
            && useCallback.returnValue == ActionResult.FAIL)
        {
            EnchantmentLoader.enchantments.forEach {
                val listener = it as? AirUseListener ?: return@forEach
                if (context.stack.hasEnchantment(it))
                    listener.onUseInAir(context.world as ServerWorld, context.player as ServerPlayerEntity, context.stack)
            }
        }
    }
}