package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.TntEntity
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.event.listener.HarvestListener

class Demolition : ChargeEnchantment(Rarity.VERY_RARE), HarvestListener, AirUseListener, AppendTooltipListener {
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
        val velocity = player.rotationVector.multiply(3.0)
        val pos = player.eyePos

        val tnt = TntEntity(world, pos.x, pos.y, pos.z, player).apply {
            this.velocity = velocity
        }

        return tnt
    }

    override fun getTooltipColor(): Formatting = Formatting.RED

    override fun getMaxCharge(level: Int): Int = 200
    override fun getChargeDecrement(): Int = 20
    override fun chargeToString(charge: Int): String = "%.2f".format(charge / 20f)
}