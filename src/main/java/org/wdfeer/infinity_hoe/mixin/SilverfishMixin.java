package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.mob.SilverfishEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wdfeer.infinity_hoe.enchantment.unique.rare.Pestilence;

@Mixin(SilverfishEntity.class)
class SilverfishMixin {
    @Inject(method = "initGoals", at = @At("RETURN"))
    private void injectGoals(CallbackInfo ci) {
        Pestilence.INSTANCE.silverfishInitGoalsMixin((SilverfishEntity) (Object) this);
    }
}
