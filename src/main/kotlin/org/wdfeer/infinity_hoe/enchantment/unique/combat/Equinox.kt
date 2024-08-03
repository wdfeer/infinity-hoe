package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import kotlin.random.Random

class Equinox : HoeEnchantment(Rarity.RARE), OnHitEnchantment {
    private val statusDuration: Int = 100
    override fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        if (Random.nextBoolean())
            target.addStatusEffect(StatusEffectInstance(StatusEffects.WEAKNESS, statusDuration), attacker)
        else
            attacker.addStatusEffect(StatusEffectInstance(StatusEffects.STRENGTH, statusDuration), attacker)
    }


    override fun getPath(): String = "equinox"
    override fun getMinPower(level: Int): Int = 12
    override fun getMaxPower(level: Int): Int = 50
}