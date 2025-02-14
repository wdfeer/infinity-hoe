package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wdfeer.infinity_hoe.enchantment.unique.very_rare.Automata;

@Mixin(ItemEntity.class)
class ItemEntityMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void injectTick(CallbackInfo ci) {
        Automata.INSTANCE.mixinItemEntityTick((ItemEntity) (Object) this);
    }
}
