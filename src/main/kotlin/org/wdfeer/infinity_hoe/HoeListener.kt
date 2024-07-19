package org.wdfeer.infinity_hoe

import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.ModEnchantments
import org.wdfeer.infinity_hoe.enchantment.Pesticide
import org.wdfeer.infinity_hoe.tilling.InfinityTiller
import org.wdfeer.infinity_hoe.util.hasEnchantment

object HoeListener {
    fun preUseOnBlock(
        context: ItemUsageContext
    ) {
        if (context.stack.hasEnchantment(ModEnchantments.infinity))
            InfinityTiller.preTrigger(context.world, context.stack, context.blockPos)
    }

    fun postUseOnBlock(
        context: ItemUsageContext,
        cir: CallbackInfoReturnable<ActionResult>
    ) {
        if (cir.returnValue != ActionResult.CONSUME || context.player !is ServerPlayerEntity) return

        if (context.stack.hasEnchantment(ModEnchantments.infinity))
            InfinityTiller.trigger(context.world, context.stack, context.blockPos, context.player as ServerPlayerEntity)

        if (context.stack.hasEnchantment(ModEnchantments.pesticide) && context.world is ServerWorld)
            Pesticide.onTill(context.world as ServerWorld, context.player as ServerPlayerEntity, context.stack, context.blockPos)
    }
}