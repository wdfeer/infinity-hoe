package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.TntEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment

class Demolition : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 20..60

    override fun getPath(): String = "demolition"

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        world.spawnEntity(createTnt(world, player))

        return true
    }

    private fun createTnt(
        world: ServerWorld,
        player: ServerPlayerEntity
    ): TntEntity {
        val velocity = player.rotationVector
        val pos = player.eyePos

        return TntEntity(world, pos.x, pos.y, pos.z, player).apply {
            this.velocity = velocity
        }
    }

    override fun getTooltipColor(): Formatting = Formatting.RED

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 25
    override fun chargeToString(charge: Int): String = "%.2f".format(charge / getChargeDecrement())
}