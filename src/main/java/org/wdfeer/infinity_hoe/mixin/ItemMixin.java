package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.event.HoeTooltip;
import org.wdfeer.infinity_hoe.event.HoeUse;

import java.util.List;

@Mixin(Item.class)
class ItemMixin {
    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    private void injectUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        HoeUse.INSTANCE.mixinItemUse(world, user, hand, cir);
    }

    @Inject(method = "appendTooltip", at = @At("RETURN"))
    private void injectAppendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        HoeTooltip.INSTANCE.mixinAppendTooltip(stack, tooltip);
    }
}
