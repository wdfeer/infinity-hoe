package org.wdfeer.infinity_hoe.enchantment.bc

import net.bettercombat.BetterCombat
import net.bettercombat.api.WeaponAttributes
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.Reaper
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.Sickle
import org.wdfeer.infinity_hoe.util.hasEnchantment
import org.wdfeer.infinity_hoe.util.ifElse

abstract class BetterCombatEnchantment : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        private fun canRegister(): Boolean = FabricLoader.getInstance().isModLoaded(BetterCombat.MODID)

        val enchantments: List<BetterCombatEnchantment> = canRegister().ifElse(listOf(
            Reaper(),
            Sickle()
        ), emptyList())
    }

    protected abstract val attributes: WeaponAttributes

    fun mixinGetAttributes(stack: ItemStack, cir: CallbackInfoReturnable<WeaponAttributes>) {
        if (cir.returnValue == null && stack.hasEnchantment(this)) {
            cir.returnValue = attributes
        }
    }

    override fun getPowerRange(level: Int): IntRange = 16..40

    override fun canAccept(other: Enchantment?): Boolean = other !is BetterCombatEnchantment
}