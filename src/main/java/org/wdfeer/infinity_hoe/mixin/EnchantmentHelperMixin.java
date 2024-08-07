package org.wdfeer.infinity_hoe.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment;

import java.util.List;

@Mixin(EnchantmentHelper.class)
class EnchantmentHelperMixin {
    @Inject(method = "generateEnchantments", at = @At("RETURN"), cancellable = true)
    private static void injectGenerateEnchantments(Random random,
                                                   ItemStack stack,
                                                   int level,
                                                   boolean treasureAllowed,
                                                   CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir){
        // Delete HoeEnchantments on non-hoe items because cannot create a custom EnchantmentTarget (should be changed since 1.20.5)

        if (stack.getItem() instanceof HoeItem) return;

        List<EnchantmentLevelEntry> oldList = cir.getReturnValue();
        List<EnchantmentLevelEntry> newList = oldList.stream().filter(entry -> !(entry.enchantment instanceof HoeEnchantment)).toList();

        if (oldList.size() != newList.size())
            cir.setReturnValue(newList);
    }
}
