package org.wdfeer.infinity_hoe

import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.AutoSeed
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

        val player = context.player as ServerPlayerEntity

        if (context.stack.hasEnchantment(ModEnchantments.infinity))
            InfinityTiller.trigger(context.world, context.stack, context.blockPos, player)

        if (context.world is ServerWorld) {
            val world = context.world as ServerWorld
            if (context.stack.hasEnchantment(ModEnchantments.pesticide)) {
                Pesticide.onTill(world, player, context.stack, context.blockPos)
            }

            if (context.stack.hasEnchantment(ModEnchantments.autoSeed)) {
                AutoSeed.onTill(world, player, context.blockPos)
            }
        }
    }
}