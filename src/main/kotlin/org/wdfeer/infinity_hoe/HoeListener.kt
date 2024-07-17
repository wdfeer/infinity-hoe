package org.wdfeer.infinity_hoe

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object HoeListener {
    fun onUseOnBlock(
        context: ItemUsageContext,
        cir: CallbackInfoReturnable<ActionResult>
    ) {
        // CONSUME is returned on successful till, processed by server
        if (cir.returnValue != ActionResult.CONSUME) return

        val stack = context.stack
        if (!stack.hasEnchantments()) return

        val enchantments = EnchantmentHelper.fromNbt(stack.enchantments)
        if (enchantments.contains(Infinity.instance)) {
            Infinity.onTill(context.world, context.stack, context.blockPos)
        }
    }
}