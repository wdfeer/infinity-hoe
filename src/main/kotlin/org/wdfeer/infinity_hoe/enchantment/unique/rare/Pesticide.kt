package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.emitter.HoeHit
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.event.listener.TillListener
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class Pesticide : HoeEnchantment(Rarity.RARE), HarvestListener, TillListener {
    override fun getPath(): String = "pesticide"

    override val maxLvl: Int
        get() = 5

    override fun getPowerRange(level: Int): IntRange {
        val min = 16 + level * 2
        val max = 24 + level * 2
        return min..max
    }

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature)
            trigger(world, player, hoe, pos)
    }

    override fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        trigger(world, player, hoe, pos)
    }

    private fun trigger(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos
    ) {
        getNearbyLivingEntities(world, pos.toCenterPos(), DAMAGE_RADIUS).filter { it is Monster }.forEach {
            it.damage(DamageTypes.MAGIC, getDamage(hoe), player)
            HoeHit.postHit(hoe, it, player, this)
        }
    }

    private fun getDamage(hoe: ItemStack): Float {
        return hoe.getEnchantmentLevel(EnchantmentLoader.pesticide) * 4f
    }

    companion object {
        const val DAMAGE_RADIUS = 3.0

        fun getNearbyLivingEntities(world: ServerWorld, origin: Vec3d, radius: Double): List<LivingEntity> =
            world.iterateEntities()
                .filter { it.pos.distanceTo(origin) <= radius}
                .filterIsInstance<LivingEntity>()
    }
}