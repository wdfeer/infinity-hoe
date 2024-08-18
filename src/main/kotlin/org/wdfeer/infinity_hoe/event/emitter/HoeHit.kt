package org.wdfeer.infinity_hoe.event.emitter

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.OnHitListener
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.hasEnchantment

object HoeHit {
    fun preAttack(player: ServerPlayerEntity, target: Entity) {
        if (target !is LivingEntity) return

        val inventory = player.inventory
        val hoe: ItemStack = inventory.getStack(inventory.selectedSlot)
        if (hoe.item !is HoeItem) return

        EnchantmentLoader.enchantments.filter { it is PreAttackListener }.forEach {
            if (hoe.hasEnchantment(it))
                (it as PreAttackListener).preAttack(player, target, hoe)
        }
    }

    fun postHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity, source: HoeEnchantment? = null) {
        EnchantmentLoader.enchantments.filter { it is OnHitListener }.forEach {
            if (it != source && hoe.hasEnchantment(it))
                (it as OnHitListener).onHit(hoe, target, attacker)
        }
    }
}