package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.emitter.HoeHit
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.util.DamageSourceHelper

class MysticBlade : HoeEnchantment(Rarity.RARE), PreAttackListener {
    override fun getPowerRange(level: Int): IntRange = 18..50

    override fun getPath(): String = "mystic_blade"

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        val world = target.world as? ServerWorld ?: return

        damage(target, world, player, hoe)

        Pesticide.getNearbyLivingEntities(world, target.pos, Pesticide.DAMAGE_RADIUS).filter {
            (target.type == it.type || it is Monster) && it != player
        }.forEach {
            damage(it, world, player, hoe)
        }
    }

    private fun damage(
        target: LivingEntity,
        world: ServerWorld,
        attacker: LivingEntity,
        hoe: ItemStack
    ) {
        target.damage(
            DamageSourceHelper.create(world, DamageTypes.MAGIC, attacker),
            getEnchantAmount(hoe) * damagePerEnchant
        )
        HoeHit.postHit(hoe, target, attacker, this)
        target.hurtTime = 0
    }

    private val damagePerEnchant: Float = 0.1f

    private fun getEnchantAmount(hoe: ItemStack): Int =
        EnchantmentHelper.fromNbt(hoe.enchantments).values.sumOf { it + 1 }

    override fun canAccept(other: Enchantment?): Boolean = other !is Pesticide
}