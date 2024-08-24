package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.entity.projectile.SmallFireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment

class Blazing : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
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
    ): ProjectileEntity {
        val velocity = player.rotationVector.multiply(4.0)
        val pos = player.eyePos

        val fireball = SmallFireballEntity(world, player, pos.x, pos.y, pos.z)
        fireball.velocity = velocity

        return fireball
    }

    override fun getMaxCharge(level: Int): Int = level * 50

    override fun getCooldown(): Int = 6

    override fun getTooltipColor(): Formatting = Formatting.RED
}