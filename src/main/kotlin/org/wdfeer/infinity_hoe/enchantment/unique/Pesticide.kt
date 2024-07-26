package org.wdfeer.infinity_hoe.enchantment.unique

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.damage
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class Pesticide : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "pesticide"

    override fun getMaxLevel(): Int = 5

    override fun getMinPower(level: Int): Int = 10 + level * 4

    override fun getMaxPower(level: Int): Int = 15 + level * 4

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