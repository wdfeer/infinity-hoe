package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wdfeer.infinity_hoe.event.emitter.HoeHit;

@Mixin(Item.class)
class MiningItemMixin {
    @Inject(method = "postHit", at = @At("RETURN"))
    void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci){
        if (stack.getItem() instanceof HoeItem)
            HoeHit.INSTANCE.postHit(stack, target, attacker, null);
    }
}
