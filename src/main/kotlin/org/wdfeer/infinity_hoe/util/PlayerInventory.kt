package org.wdfeer.infinity_hoe.util

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack

fun PlayerInventory.find(predicate: (ItemStack) -> Boolean): ItemStack? {
    for (i in 0 until  this.size()) {
        val stack = this.getStack(i)
        if (predicate(stack))
            return stack
    }
    return null
}