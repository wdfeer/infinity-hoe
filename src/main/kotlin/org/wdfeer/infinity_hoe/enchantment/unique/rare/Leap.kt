package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment

class Leap : UsableHarvestChargeEnchantment(Rarity.RARE) {
    companion object {
        private const val SPEED = 5.0
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val velocity = player.rotationVector.multiply(SPEED)
        player.velocity = player.velocity.multiply(0.5).add(velocity)
        player.velocityModified = true

        return true
    }

    override fun getTooltipColor(): Formatting = Formatting.AQUA

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 30
    override fun chargeToString(charge: Int): String = "%.1f".format(charge / getChargeDecrement())

    override fun getPowerRange(level: Int): IntRange = 20..60

    override fun getPath(): String = "leap"
}