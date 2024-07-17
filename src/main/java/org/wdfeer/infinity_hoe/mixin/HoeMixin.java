package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.HoeListener;

@Mixin(HoeItem.class)
public class HoeMixin {
	@Inject(at = @At("RETURN"), method = "useOnBlock")
	private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		HoeListener.INSTANCE.onUseOnBlock(context, cir);
	}
}