package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment

class CursedForge : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMaxCharge(level: Int): Int = 128
    override fun getChargeDecrement(): Int = 128
    override fun getPowerRange(level: Int): IntRange = 18..60
    override fun getPath(): String = "cursed_forge"

    override fun chargeToString(charge: Int): String = "${(charge * 100 / getChargeDecrement())}%"
    override fun getTooltipColor(): Formatting = Formatting.DARK_RED
    override fun getTooltipArgs(hoe: ItemStack): List<String> = listOf(chargeToString(getCharge(hoe)))
}