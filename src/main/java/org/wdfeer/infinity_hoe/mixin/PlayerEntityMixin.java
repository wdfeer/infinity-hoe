package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wdfeer.infinity_hoe.event.emitter.HoeHit;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void injectAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getWorld().isClient) return;

        HoeHit.INSTANCE.preAttack((ServerPlayerEntity) player, target);
    }
}
