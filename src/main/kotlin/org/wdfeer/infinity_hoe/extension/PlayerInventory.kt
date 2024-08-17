package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack

val PlayerInventory.stacks: List<ItemStack>
    get() = List(this.size()) { this.getStack(it) }