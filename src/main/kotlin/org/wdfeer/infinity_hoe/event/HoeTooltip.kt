package org.wdfeer.infinity_hoe.event

import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener

object HoeTooltip {
    fun mixinAppendTooltip(
        stack: ItemStack,
        tooltip: MutableList<Text>,
    ) {
        if (stack.item !is HoeItem) return

        EnchantmentLoader.enchantments
            .filterIsInstance<AppendTooltipListener>()
            .forEach { it.appendTooltip(stack, tooltip) }
    }
}