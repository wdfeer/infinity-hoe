package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.util.DamageTypeHelper
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class Pesticide : HoeEnchantment(Rarity.VERY_RARE) {
    companion object {
        val instance = Pesticide()

        fun register() {
            Registry.register(Registries.ENCHANTMENT, Identifier(InfinityHoe.MOD_ID, "pesticide"), instance)
        }

        fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
            for (entity in world.iterateEntities()) {
                if (checkCollision(entity, pos)) {
                    entity.damage(DamageSource(DamageTypeHelper.getRegistryEntry(world,  DamageTypes.MAGIC), player), getDamage(hoe))
                }
            }
        }

        private fun checkCollision(entity: Entity, pos: BlockPos): Boolean {
            return entity.pos.distanceTo(pos.toCenterPos()) < 3
        }

        private fun getDamage(hoe: ItemStack): Float {
            return hoe.getEnchantmentLevel(instance) * 4f
        }
    }

    override fun getMaxLevel(): Int = 5

    override fun getMinPower(level: Int): Int = 10 + level * 4

    override fun getMaxPower(level: Int): Int = 15 + level * 4

    override fun isTreasure(): Boolean = true
}