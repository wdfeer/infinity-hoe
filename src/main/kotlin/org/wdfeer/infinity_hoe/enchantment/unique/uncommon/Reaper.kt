package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.bettercombat.BetterCombat
import net.bettercombat.api.AttributesContainer
import net.bettercombat.api.WeaponAttributes
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.hasEnchantment

class Reaper : HoeEnchantment(Rarity.UNCOMMON) {
    companion object {
        private fun canRegister(): Boolean = FabricLoader.getInstance().isModLoaded(BetterCombat.MODID)

        val instance: Reaper? = canRegister().let { if (it) Reaper() else null }

        private var scytheAttributes: WeaponAttributes? = null


        fun mixinGetAttributes(stack: ItemStack, cir: CallbackInfoReturnable<WeaponAttributes>) {
            if (cir.returnValue == null && stack.hasEnchantment(instance!!)) {
                cir.returnValue = scytheAttributes
            }
        }

        fun mixinLoadContainers(
            containers: MutableMap<Identifier, AttributesContainer>
        ) {
            scytheAttributes = containers[instance!!.getIdentifier()]?.attributes()
        }
    }

    override fun getPath(): String = "reaper"
    override fun getMinPower(level: Int): Int = 8
    override fun getMaxPower(level: Int): Int = 40
}