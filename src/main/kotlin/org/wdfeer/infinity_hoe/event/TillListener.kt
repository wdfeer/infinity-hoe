package org.wdfeer.infinity_hoe.event

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.ModEnchantments
import org.wdfeer.infinity_hoe.enchantment.infinity.InfinityTiller
import org.wdfeer.infinity_hoe.util.hasEnchantment

object TillListener {
    fun preUseOnBlock( // Called from Mixin
        context: ItemUsageContext
    ) {
        if (context.stack.hasEnchantment(ModEnchantments.infinity))
            InfinityTiller.preTrigger(context.world, context.stack, context.blockPos)
    }

    fun postUseOnBlock(  // Called from Mixin
        context: ItemUsageContext,
        cir: CallbackInfoReturnable<ActionResult>
    ) {
        if (cir.returnValue != ActionResult.CONSUME
            || context.world !is ServerWorld
            || context.player !is ServerPlayerEntity) return

        onTill(context.world as ServerWorld, context.player as ServerPlayerEntity, context.stack, context.blockPos)
    }

    private fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        onTill(world, player, hoe, pos, null)
    }

    fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos, cause: Enchantment?) {
        ModEnchantments.enchantments.forEach {
            if (it != cause && hoe.hasEnchantment(it))
                it.onTill(world, player, hoe, pos)
        }
    }
}