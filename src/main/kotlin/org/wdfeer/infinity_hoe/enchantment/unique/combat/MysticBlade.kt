package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.HoeHit
import org.wdfeer.infinity_hoe.event.listener.OnHitListener
import org.wdfeer.infinity_hoe.util.DamageSourceHelper

class MysticBlade : HoeEnchantment(Rarity.RARE), OnHitListener {
    override fun getPowerRange(level: Int): IntRange = 10..50

    override fun getPath(): String = "mystic_blade"

    override fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        val world = target.world as? ServerWorld ?: return

        damage(target, world, attacker, hoe)

        Pesticide.getNearbyLivingEntities(world, target.pos, Pesticide.DAMAGE_RADIUS).filter {
            it is Monster && it != target
        }.forEach {
            damage(it, world, attacker, hoe)
        }
    }

    private fun damage(
        target: LivingEntity,
        world: ServerWorld,
        attacker: LivingEntity,
        hoe: ItemStack
    ) {
        target.hurtTime = 0
        target.damage(
            DamageSourceHelper.create(world, DamageTypes.MAGIC, attacker),
            getEnchantAmount(hoe) * damagePerEnchant
        )
        HoeHit.postHit(hoe, target, attacker, this)
    }

    private val damagePerEnchant: Float = 0.1f

    private fun getEnchantAmount(hoe: ItemStack): Int =
        EnchantmentHelper.fromNbt(hoe.enchantments).values.sumOf { it + 1 }

    override fun canAccept(other: Enchantment?): Boolean = other !is Pesticide
}