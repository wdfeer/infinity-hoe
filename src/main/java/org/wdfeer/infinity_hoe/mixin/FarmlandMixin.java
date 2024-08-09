package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wdfeer.infinity_hoe.enchantment.unique.common.Untill;

@Mixin(FarmlandBlock.class)
public class FarmlandMixin {
    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void injectOnLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (entity instanceof ServerPlayerEntity player && Untill.Companion.canCancelLandUntill(player))
            ci.cancel();
    }
}
