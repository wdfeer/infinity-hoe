package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment

object Fireblast : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override val maxLvl: Int get() = 5
    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * level * 16
    override fun getChargeDecrement(): Int = 8
    override fun chargeToString(charge: Int): String = "${(charge / getChargeDecrement())}"
    override fun getPowerRange(level: Int): IntRange = 22 + 3 * level..60
    override fun getPath(): String = "fireblast"
}