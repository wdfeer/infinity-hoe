package org.wdfeer.infinity_hoe.event

import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.Infinity
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.hasEnchantment

object HoeUse {
    fun preUseOnBlock( // Called from Mixin
        context: ItemUsageContext
    ) {
        if (context.stack.hasEnchantment(EnchantmentLoader.infinity))
            Infinity.preTrigger(context.world, context.stack, context.blockPos)
    }

    fun postUseOnBlock(  // Called from Mixin
        context: ItemUsageContext,
        cir: CallbackInfoReturnable<ActionResult>
    ) {
        if (context.world is ServerWorld
            && context.player is ServerPlayerEntity
            && cir.returnValue == ActionResult.CONSUME)
            onTill(context.world as ServerWorld, context.player as ServerPlayerEntity, context.stack, context.blockPos, null)
        else
            checkUntill(context.world, context.player, context.stack, context.blockPos, cir)
    }

    fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos, cause: Enchantment?) {
        EnchantmentLoader.enchantments.forEach {
            if (it != cause && hoe.hasEnchantment(it))
                it.onTill(world, player, hoe, pos)
        }
    }

    private fun checkUntill(
        world: World,
        player: PlayerEntity?,
        hoe: ItemStack,
        pos: BlockPos,
        cir: CallbackInfoReturnable<ActionResult>
    ) {
        val state = world.getBlockState(pos)
        if (state.block == Blocks.FARMLAND && hoe.hasEnchantment(EnchantmentLoader.untill)) {
            if (world.isClient)
                cir.returnValue = ActionResult.SUCCESS
            else
            {
                world.setBlockState(pos, Blocks.DIRT.defaultState)
                if (player is ServerPlayerEntity)
                    hoe.damage(player)
                cir.returnValue = ActionResult.CONSUME
            }
        }
    }
}