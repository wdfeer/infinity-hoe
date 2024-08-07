package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.rare.SoulSiphon
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.OnHitListener
import kotlin.random.Random
import kotlin.random.nextInt

class Equinox : HoeEnchantment(Rarity.RARE), OnHitListener {
    private val statusDuration: Int = 100
    override fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        val roll = Random.nextInt(1..3)
        when (roll) {
            1 -> target.addStatusEffect(StatusEffectInstance(StatusEffects.WEAKNESS, statusDuration), attacker)
            2 -> attacker.addStatusEffect(StatusEffectInstance(StatusEffects.STRENGTH, statusDuration), attacker)
            3 -> (target.world as? ServerWorld)?.let { GrowthAcceleration.proc(it, target.blockPos, 1) }
        }
    }


    override fun getPath(): String = "equinox"
    override fun getPowerRange(level: Int): IntRange = 12..50


    override fun canAccept(other: Enchantment?): Boolean = other !is GrowthAcceleration && other !is SoulSiphon
}