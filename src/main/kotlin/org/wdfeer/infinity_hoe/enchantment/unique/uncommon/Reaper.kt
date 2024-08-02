package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.bettercombat.BetterCombat
import net.bettercombat.api.WeaponAttributes
import net.bettercombat.logic.WeaponRegistry
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.hasEnchantment
import java.lang.reflect.Method

class Reaper : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        fun canRegister(): Boolean = FabricLoader.getInstance().isModLoaded(BetterCombat.MODID)

        fun getAttributesMixin(stack: ItemStack, cir: CallbackInfoReturnable<WeaponAttributes>) {
            if (cir.returnValue == null && stack.hasEnchantment(EnchantmentLoader.reaper)) {
                // TODO: Don't use reflection

                val method: Method = WeaponRegistry::class.java.getDeclaredMethod("getAttributes", Identifier::class.java)

                method.isAccessible = true

                cir.returnValue = method.invoke(WeaponRegistry::class.java, Identifier(InfinityHoe.MOD_ID, "reaper")) as? WeaponAttributes
            }
        }
    }

    override fun getPath(): String = "reaper"
    override fun getMinPower(level: Int): Int = 8
    override fun getMaxPower(level: Int): Int = 40
}