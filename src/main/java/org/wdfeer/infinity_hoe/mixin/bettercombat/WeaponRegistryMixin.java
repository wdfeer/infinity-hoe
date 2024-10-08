package org.wdfeer.infinity_hoe.mixin.bettercombat;

import net.bettercombat.api.WeaponAttributes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.enchantment.parent.BetterCombatEnchantment;

@Mixin(net.bettercombat.logic.WeaponRegistry.class)
class WeaponRegistryMixin {
    @Inject(at = @At("RETURN"),
            method = "getAttributes(Lnet/minecraft/item/ItemStack;)Lnet/bettercombat/api/WeaponAttributes;",
            cancellable = true)
    private static void getAttributes(ItemStack itemStack, CallbackInfoReturnable<WeaponAttributes> cir){
        for (var enchant : BetterCombatEnchantment.Companion.getEnchantments()) {
            enchant.mixinGetAttributes(itemStack, cir);
        }
    }
}
