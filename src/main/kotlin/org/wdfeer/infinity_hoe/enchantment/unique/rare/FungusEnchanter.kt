package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HoldTicker

object FungusEnchanter : ChargeEnchantment(Rarity.RARE), HoldTicker {
    private val mushroomEnchantments: List<Enchantment> by lazy { listOf(
        // TODO: add mushroom enchantments
    ) }

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % 20L == 0L

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        TODO("Not yet implemented")
    }


    override fun getMaxCharge(level: Int): Int = 3600
    override fun getPowerRange(level: Int): IntRange = 0..25
    override fun getPath(): String = "fungus_enchanter"
    override fun getTooltipColor(): Formatting = Formatting.DARK_GREEN
}