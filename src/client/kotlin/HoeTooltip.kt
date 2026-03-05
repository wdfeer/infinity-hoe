package org.wdfeer.infinity_hoe.client

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.extension.hasEnchantment

object HoeTooltip {
    fun initialize() {
        ItemTooltipCallback.EVENT.register { itemStack, _, _, list ->
            appendTooltip(itemStack, list)
        }
    }

    private fun appendTooltip(
        stack: ItemStack,
        tooltip: MutableList<Text>,
    ) {
        if (stack.item !is HoeItem) return

        EnchantmentLoader.enchantments
            .filter { stack.hasEnchantment(it) }
            .filterIsInstance<AppendTooltipListener>()
            .forEach { it.appendTooltip(stack, tooltip) }
    }
}