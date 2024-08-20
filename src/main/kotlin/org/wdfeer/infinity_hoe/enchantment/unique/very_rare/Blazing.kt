package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.event.listener.HarvestListener

class Blazing : UsableHarvestChargeEnchantment(Rarity.VERY_RARE), HarvestListener, AirUseListener, AppendTooltipListener {
    override val maxLvl: Int
        get() = 2

    override fun getPowerRange(level: Int): IntRange = 20 + 5 * level..60

    override fun getPath(): String = "blazing"

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        world.spawnEntity(createFireball(world, player))

        return true
    }

    private fun createFireball(
        world: ServerWorld,
        player: ServerPlayerEntity
    ): FireballEntity {
        val velocity = player.rotationVector.multiply(3.0)
        val pos = player.eyePos

        val fireball = FireballEntity(world, player, velocity.x, velocity.y, velocity.z, 0)
        fireball.setPosition(pos)

        return fireball
    }

    override fun getTooltipColor(): Formatting = Formatting.RED
}