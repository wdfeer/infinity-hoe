package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.mob.Monster
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.HoeListener
import org.wdfeer.infinity_hoe.util.DamageTypeHelper
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.hasEnchantment

class Pesticide : HoeEnchantment(Rarity.VERY_RARE) {
    companion object {
        fun initialize() {
            HoeListener.listen(::onTill)
        }

        private fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
            if (hoe.hasEnchantment(ModEnchantments.pesticide))
                for (entity in world.iterateEntities()) {
                    if (entity is Monster && checkCollision(entity, pos)) {
                        entity.damage(DamageSource(DamageTypeHelper.getRegistryEntry(world,  DamageTypes.MAGIC), player), getDamage(hoe))
                    }
                }
        }

        private fun checkCollision(entity: Entity, pos: BlockPos): Boolean {
            return entity.pos.distanceTo(pos.toCenterPos()) < 3
        }

        private fun getDamage(hoe: ItemStack): Float {
            return hoe.getEnchantmentLevel(ModEnchantments.pesticide) * 4f
        }
    }

    override fun getPath(): String = "pesticide"

    override fun getMaxLevel(): Int = 5

    override fun getMinPower(level: Int): Int = 10 + level * 4

    override fun getMaxPower(level: Int): Int = 15 + level * 4

    override fun isTreasure(): Boolean = true
}