package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.emitter.HoeHit
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage
import org.wdfeer.infinity_hoe.extension.enchantmentMap

object MysticBlade : HoeEnchantment, PreAttackListener {
    override fun getPath(): String = "mystic_blade"

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        val world = target.entityWorld as? ServerWorld ?: return

        damage(target, player, hoe)

        Pesticide.getNearbyLivingEntities(world, target.entityPos, Pesticide.DAMAGE_RADIUS).filter {
            (target.type == it.type || it is Monster) && it != player
        }.forEach {
            damage(it, player, hoe)
        }
    }

    private fun damage(
        target: LivingEntity,
        attacker: LivingEntity,
        hoe: ItemStack
    ) {
        target.damage(
            DamageTypes.MAGIC,
            getEnchantAmount(hoe) * DAMAGE_PER_ENCHANTMENT,
            attacker
        )
        target.hurtTime = 0

        HoeHit.postHit(hoe, target, attacker, this)
    }

    private const val DAMAGE_PER_ENCHANTMENT: Float = 0.1f

    private fun getEnchantAmount(hoe: ItemStack): Int =
        hoe.enchantmentMap.map { (_, value) -> value }.sum()
}