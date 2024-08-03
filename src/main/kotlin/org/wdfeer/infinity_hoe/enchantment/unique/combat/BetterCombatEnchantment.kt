package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.bettercombat.BetterCombat
import net.bettercombat.api.AttributesContainer
import net.bettercombat.api.WeaponAttributes
import net.bettercombat.logic.WeaponRegistry
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.hasEnchantment
import org.wdfeer.infinity_hoe.util.ifElse

private fun canRegister(): Boolean = FabricLoader.getInstance().isModLoaded(BetterCombat.MODID)

abstract class BetterCombatEnchantment : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        val enchantments: List<BetterCombatEnchantment> = canRegister().ifElse(listOf(
            Reaper()
        ), emptyList())
    }

    private var attributes: WeaponAttributes? = null

    fun mixinGetAttributes(stack: ItemStack, cir: CallbackInfoReturnable<WeaponAttributes>) {
        if (cir.returnValue == null && stack.hasEnchantment(this)) {
            cir.returnValue = attributes
        }
    }

    fun mixinLoadContainers(
        containers: MutableMap<Identifier, AttributesContainer>
    ) {
        val container = containers[getIdentifier()]
        attributes = WeaponRegistry.resolveAttributes(getIdentifier(), container)
    }

    override fun getMinPower(level: Int): Int = 8
    override fun getMaxPower(level: Int): Int = 40
}