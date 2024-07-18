package org.wdfeer.infinity_hoe

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object HoeListener {
    fun preUseOnBlock(
        context: ItemUsageContext
    ) {
        if (isInfinityHoe(context))
            ChainTiller.preTrigger(context.world, context.stack, context.blockPos)
    }

    fun postUseOnBlock(
        context: ItemUsageContext,
        cir: CallbackInfoReturnable<ActionResult>
    ) {
            if (cir.returnValue != ActionResult.CONSUME) return
            if (isInfinityHoe(context))
                ChainTiller.trigger(context.world, context.stack, context.blockPos, context.player as ServerPlayerEntity)
    }

    private fun isInfinityHoe(context: ItemUsageContext): Boolean {
        if (context.player !is ServerPlayerEntity) return false

        val stack = context.stack
        if (!stack.hasEnchantments()) return false

        val enchantments = EnchantmentHelper.fromNbt(stack.enchantments)
        return enchantments.contains(Infinity.instance)
    }
}