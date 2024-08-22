package org.wdfeer.infinity_hoe.extension

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity

val ServerPlayerEntity.inventoryStacks: List<ItemStack>
    get() = this.inventory.stacks

val PlayerInventory.stacks: List<ItemStack>
    get() = List(this.size()) { this.getStack(it) }