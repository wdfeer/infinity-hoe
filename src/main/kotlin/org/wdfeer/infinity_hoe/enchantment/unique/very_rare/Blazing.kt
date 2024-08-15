package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener

class Blazing : HoeEnchantment(Rarity.VERY_RARE), AirUseListener {
    override fun getPowerRange(level: Int): IntRange = 20..70

    override fun getPath(): String = "blazing"

    override fun onUseInAir(world: ServerWorld, player: ServerPlayerEntity, stack: ItemStack) {
        fun createFireballEntity(): FireballEntity {
            val fireball = FireballEntity(world, player, 0.0, 0.0, 0.0, 0)

            val playerFacing = player.rotationClient
            val playerPos = player.pos
            val forwardVector = Vec3d(
                -Math.sin(Math.toRadians(playerFacing.y.toDouble())),
                0.0,
                Math.cos(Math.toRadians(playerFacing.y.toDouble()))
            )

            fireball.updatePosition(
                playerPos.x + forwardVector.x * 2.0,
                playerPos.y + player.eyeY - 0.10000000149011612,
                playerPos.z + forwardVector.z * 2.0
            )

            fireball.setVelocity(forwardVector.x * 3.0, 0.0, forwardVector.z * 3.0)

            return fireball
        }

        world.spawnEntity(createFireballEntity())
    }
}