package org.wdfeer.infinity_hoe

import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.AutoSeed
import org.wdfeer.infinity_hoe.enchantment.ModEnchantments
import org.wdfeer.infinity_hoe.enchantment.Pesticide
import org.wdfeer.infinity_hoe.tilling.InfinityTillAction
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
        if (cir.returnValue != ActionResult.CONSUME
            || context.world !is ServerWorld
            || context.player !is ServerPlayerEntity) return

        onTill(context.world as ServerWorld, context.player as ServerPlayerEntity, context.stack, context.blockPos)
    }

    fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos, infinity: InfinityTillAction? = null) {
        if (hoe.hasEnchantment(ModEnchantments.infinity))
            InfinityTiller.trigger(world, hoe, pos, player)

        if (hoe.hasEnchantment(ModEnchantments.pesticide)) {
            Pesticide.onTill(world, player, hoe, pos)
        }

        if (hoe.hasEnchantment(ModEnchantments.autoSeed)) {
            AutoSeed.onTill(world, player, pos, infinity)
        }
    }
}