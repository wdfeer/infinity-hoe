package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.TickListener
import kotlin.random.Random

object Fireblast : UsableHarvestChargeEnchantment(Rarity.VERY_RARE), TickListener {
    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        world.spawnEntity(createFireball(world, player))

        return true
    }

    private var splittingFireballs: MutableList<FireballEntity> = mutableListOf()
    private fun createFireball(
        world: ServerWorld,
        player: ServerPlayerEntity
    ): ProjectileEntity {
        val velocity = player.rotationVector.multiply(4.0)

        val fireball = FireballEntity(world, player, velocity.x, velocity.y, velocity.z, 2)
        fireball.setPosition(player.eyePos)
        splittingFireballs.add(fireball)

        return fireball
    }

    private const val DETONATION_DISTANCE = 8
    override fun postWorldTick(world: ServerWorld) {
        splittingFireballs.forEach { e -> if (e.owner!!.distanceTo(e) > DETONATION_DISTANCE) detonateFireball(e) }
        splittingFireballs.removeIf { !it.isAlive }
    }

    private fun detonateFireball(fireball: FireballEntity) {
        val world = fireball.world as? ServerWorld ?: return
        repeat(3) {
            val velocity = fireball.velocity.rotateY(Random.nextFloat() - 0.5f)
            val entity = FireballEntity(
                world,
                fireball.owner as ServerPlayerEntity,
                velocity.x,
                velocity.y,
                velocity.z,
                2
            )
            entity.setPosition(fireball.pos.add(velocity.multiply(1.2)))
            world.spawnEntity(entity)
        }

        fireball.remove(Entity.RemovalReason.KILLED)
    }

    override val maxLvl: Int get() = 5
    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * level * 16
    override fun getChargeDecrement(): Int = 8
    override fun chargeToString(charge: Int): String = "${(charge / getChargeDecrement())}"
    override fun getTooltipColor(): Formatting = Formatting.RED
    override fun getPowerRange(level: Int): IntRange = 22 + 3 * level..60
    override fun getPath(): String = "fireblast"
}