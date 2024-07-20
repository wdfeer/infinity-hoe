package org.wdfeer.infinity_hoe

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

object HoeListener {
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

    private val tillListeners: MutableSet<(ServerWorld, ServerPlayerEntity, ItemStack, BlockPos) -> Unit> = mutableSetOf()
    fun listen(listener: (world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) -> Unit) =
        tillListeners.add(listener)

    fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        tillListeners.forEach { it.invoke(world, player, hoe, pos) }
    }
}