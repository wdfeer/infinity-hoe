package org.wdfeer.infinity_hoe.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import java.util.*


object AttributeHelper {
    private fun getAttributeModifiers(head: NbtCompound): NbtList {
        return head.getList("AttributeModifiers", 10)
    }

    private fun getIndexWithUuid(attributes: NbtList, uuid: UUID): Int {
        val length = attributes.size
        for (i in 0 until length) {
            val uuidNbt = attributes.getCompound(i)["UUID"]
            if (uuidNbt != null && uuid == NbtHelper.toUuid(uuidNbt)) {
                return i
            }
        }
        return -1
    }

    private fun removeAttributeWithUuid(attributes: NbtList, uuid: UUID) {
        val index = getIndexWithUuid(attributes, uuid)
        if (index != -1) attributes.removeAt(index)
    }

    fun removeAttributeWithUuid(stack: ItemStack, uuid: UUID) {
        removeAttributeWithUuid(getAttributeModifiers(stack.getOrCreateNbt()), uuid)
    }
}