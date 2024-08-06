package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.event.listener.TillListener
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class Pesticide : HoeEnchantment(Rarity.RARE), HarvestListener, TillListener {
    override fun getPath(): String = "pesticide"

    override val maxLvl: Int
        get() = 5

    override fun getPowerRange(level: Int): IntRange {
        val min = 10 + level * 4
        val max = 16 + level * 4
        return min..max
    }

    override fun isTreasure(): Boolean = true

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
        for (entity in world.iterateEntities()) {
            if (entity is Monster && checkCollision(entity, pos)) {
                entity.damage(DamageTypes.MAGIC, getDamage(hoe), player)
            }
        }
    }


    private fun checkCollision(entity: Entity, pos: BlockPos): Boolean {
        return entity.pos.distanceTo(pos.toCenterPos()) < 3
    }

    private fun getDamage(hoe: ItemStack): Float {
        return hoe.getEnchantmentLevel(EnchantmentLoader.pesticide) * 4f
    }
}