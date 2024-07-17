package org.wdfeer.infinity_hoe

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

class Infinity : Enchantment(Rarity.RARE, EnchantmentTarget.DIGGER, arrayOf(EquipmentSlot.MAINHAND)) {
    companion object {
        val instance = Infinity()

        fun register() {
            Registry.register(Registries.ENCHANTMENT, Identifier(InfinityHoe.MOD_ID, "hoe_infinity"), instance)
        }
    }

    override fun getMinPower(level: Int): Int {
        return 20
    }

    override fun getMaxPower(level: Int): Int {
        return 50
    }

    override fun isAcceptableItem(stack: ItemStack?): Boolean {
        return stack?.item is HoeItem
    }

    override fun isTreasure(): Boolean {
        return true
    }
}