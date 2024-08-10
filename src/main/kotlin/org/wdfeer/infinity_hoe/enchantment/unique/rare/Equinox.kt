package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.catalyze.CropCatalyzer
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.OnHitListener

class Equinox : HoeEnchantment(Rarity.RARE), OnHitListener {
    companion object {
        private const val STATUS_DURATION: Int = 100
    }

    override fun onHit(hoe: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        if (attacker !is ServerPlayerEntity || attacker.world !is ServerWorld) return

        val actions: List<() -> Unit> = listOf(
            { target.addStatusEffect(StatusEffectInstance(StatusEffects.WEAKNESS, STATUS_DURATION), attacker) },
            { attacker.addStatusEffect(StatusEffectInstance(StatusEffects.STRENGTH, STATUS_DURATION), attacker) },
            { CropCatalyzer.trigger(attacker.world as ServerWorld, attacker, 1, hoe) }
        )

        actions.random()()
    }


    override fun getPath(): String = "equinox"
    override fun getPowerRange(level: Int): IntRange = 18..50


    override fun canAccept(other: Enchantment?): Boolean = other !is GrowthAcceleration && other !is SoulSiphon
}