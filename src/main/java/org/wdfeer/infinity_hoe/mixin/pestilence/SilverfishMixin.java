package org.wdfeer.infinity_hoe.mixin.pestilence;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wdfeer.infinity_hoe.enchantment.unique.rare.Pestilence;

@Mixin(SilverfishEntity.class)
class SilverfishMixin {
    @Inject(method = "initGoals", at = @At("RETURN"))
    private void injectGoals(CallbackInfo ci) {
        GoalSelector targetSelector = ((MobEntityMixin) this).getTargetSelector();
        targetSelector.clear((goal -> goal instanceof ActiveTargetGoal));
        targetSelector.add(2, new ActiveTargetGoal<>((SilverfishEntity) (Object) this,
                PlayerEntity.class,
                true,
                livingEntity -> livingEntity instanceof ServerPlayerEntity player && !Pestilence.INSTANCE.silverfishIgnorePlayer(player)));
    }
}
