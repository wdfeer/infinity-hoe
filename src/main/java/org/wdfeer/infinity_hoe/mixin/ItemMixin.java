package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.event.emitter.HoeTooltip;
import org.wdfeer.infinity_hoe.event.emitter.HoeUse;

import java.util.function.Consumer;

@Mixin(Item.class)
class ItemMixin {
    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    private void injectUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        HoeUse.INSTANCE.mixinItemUse(world, user, hand, cir);
    }

    @Inject(method = "appendTooltip", at = @At("RETURN"))
    private void injectAppendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type, CallbackInfo ci) {
        HoeTooltip.INSTANCE.mixinAppendTooltip(stack, tooltip);
    }
}
