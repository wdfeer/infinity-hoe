package org.wdfeer.infinity_hoe.enchantment.bc

import net.bettercombat.BetterCombat
import net.bettercombat.api.WeaponAttributes
import net.bettercombat.api.WeaponAttributesHelper
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.Reaper
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.Sickle
import org.wdfeer.infinity_hoe.io.IO.getFileInJar
import org.wdfeer.infinity_hoe.util.hasEnchantment
import org.wdfeer.infinity_hoe.util.ifElse

abstract class BetterCombatEnchantment : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        private fun canRegister(): Boolean = FabricLoader.getInstance().isModLoaded(BetterCombat.MODID)

        val enchantments: List<BetterCombatEnchantment> = canRegister().ifElse(listOf(
            Reaper(),
            Sickle()
        ), emptyList())

        val attributes: Map<BetterCombatEnchantment, WeaponAttributes> = readAttributes()

        private fun readAttributes() = enchantments.associateWith {
            val stream = getFileInJar("extra/${it.getPath()}.json")
            val reader = stream?.reader()
            WeaponAttributesHelper.decode(reader).attributes()
        }
    }

    fun mixinGetAttributes(stack: ItemStack, cir: CallbackInfoReturnable<WeaponAttributes>) {
        if (cir.returnValue == null && stack.hasEnchantment(this)) {
            cir.returnValue = attributes[this]
        }
    }

    override fun getPowerRange(level: Int): IntRange = 16..40

    override fun canAccept(other: Enchantment?): Boolean = other !is BetterCombatEnchantment
}