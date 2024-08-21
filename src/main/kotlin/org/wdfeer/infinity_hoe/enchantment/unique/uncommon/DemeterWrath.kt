package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.demeter.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.emitter.HoeHit
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.util.DamageSourceHelper
import kotlin.math.log10

class DemeterWrath : DemeterEnchantment(), PreAttackListener {
    override fun getPath(): String = "demeter_wrath"

    private fun getDamage(world: ServerWorld, player: ServerPlayerEntity): Float = log10(getPlayerHarvestCount(world, player).toFloat() + 1f)

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        target.damage(
            DamageSourceHelper.create(player.serverWorld, DamageTypes.MAGIC, player),
            getDamage(player.serverWorld, player)
        )
        HoeHit.postHit(hoe, target, player, this)
        target.hurtTime = 0
    }
}