package org.wdfeer.infinity_hoe.event.listener

import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity

interface PreAttackListener {
    fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack)
}