package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.event.emitter.PlayerDamageTaken;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"))
    void injectPreDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerDamageTaken.INSTANCE.mixinPreDamageTaken((ServerPlayerEntity) (Object) this, amount);
    }

    @Inject(method = "damage", at = @At("RETURN"))
    void injectPostDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerDamageTaken.INSTANCE.mixinPostDamageTaken((ServerPlayerEntity) (Object) this, amount);
    }
}
