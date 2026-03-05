package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.event.emitter.HoeUse;

@Mixin(Item.class)
abstract
class ItemMixin {
    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    private void injectUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        HoeUse.INSTANCE.mixinItemUse(world, user, hand, cir);
    }
}
