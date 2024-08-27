package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.MendingEnchantment
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.CropCatalyzer
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.*
import java.util.*
import kotlin.random.Random

class Decompose : HoeEnchantment(Rarity.RARE), HoldTicker {
    companion object {
        private const val INTERVAL: Int = 60
    }

    override fun getPath(): String = "decompose"
    override fun getPowerRange(level: Int): IntRange = 16..50
    override fun canAccept(other: Enchantment?): Boolean = other !is MendingEnchantment


    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % INTERVAL == 0L

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        // TODO: Implement logic
    }
}