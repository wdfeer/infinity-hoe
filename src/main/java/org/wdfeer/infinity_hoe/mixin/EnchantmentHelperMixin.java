package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment;

import java.util.List;

@Mixin(EnchantmentHelper.class)
class EnchantmentHelperMixin {
    @Inject(method = "getPossibleEntries", at = @At("HEAD"))
    private static void headGetPossibleEntries(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        HoeEnchantment.Companion.setStackBeingProcessed(stack);
    }

    @Inject(method = "getPossibleEntries", at = @At("RETURN"))
    private static void returnGetPossibleEntries(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        HoeEnchantment.Companion.setStackBeingProcessed(null);
    }
}
