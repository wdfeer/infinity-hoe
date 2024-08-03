package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

interface OnHitEnchantment {
    fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity)
}