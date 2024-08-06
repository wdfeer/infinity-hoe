package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

interface OnHitListener {
    fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity)
}