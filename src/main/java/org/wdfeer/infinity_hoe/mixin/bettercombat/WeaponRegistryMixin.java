package org.wdfeer.infinity_hoe.mixin.bettercombat;

import net.bettercombat.api.AttributesContainer;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wdfeer.infinity_hoe.enchantment.unique.combat.BetterCombatEnchantment;

import java.util.Map;

@Mixin(net.bettercombat.logic.WeaponRegistry.class)
class WeaponRegistryMixin {
    @Shadow
    static Map<Identifier, AttributesContainer> containers;

    @Inject(at = @At("RETURN"),
            method = "getAttributes(Lnet/minecraft/item/ItemStack;)Lnet/bettercombat/api/WeaponAttributes;",
            cancellable = true)
    private static void getAttributes(ItemStack itemStack, CallbackInfoReturnable<WeaponAttributes> cir){
        for (var enchant : BetterCombatEnchantment.Companion.getEnchantments()) {
            enchant.mixinGetAttributes(itemStack, cir);
        }
    }

    @Inject(at = @At("RETURN"),
            method = "loadContainers")
    private static void loadContainers(ResourceManager resourceManager, CallbackInfo ci) {
        for (var enchant : BetterCombatEnchantment.Companion.getEnchantments()) {
            enchant.mixinLoadContainers(containers);
        }
    }
}
