package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.item.ItemStack
import net.minecraft.text.Text

interface AppendTooltipListener {
    fun appendTooltip(stack: ItemStack, tooltip: MutableList<Text>)
}