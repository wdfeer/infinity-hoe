package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.enchantment.unique.rare.DemeterAegis;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"))
    void injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        DemeterAegis.Companion.preDamage((ServerPlayerEntity) (Object) this);
    }
}
