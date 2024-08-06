package org.wdfeer.infinity_hoe.event

import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.listener.OnHitListener
import org.wdfeer.infinity_hoe.util.hasEnchantment

object HoeHit {
    fun postHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        EnchantmentLoader.enchantments.filter { it is OnHitListener }.forEach {
            if (hoe.hasEnchantment(it))
                (it as OnHitListener).onHit(hoe, target, attacker)
        }
    }
}