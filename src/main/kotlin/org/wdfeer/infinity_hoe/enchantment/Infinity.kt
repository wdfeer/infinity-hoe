package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.infinity.InfinityTiller

class Infinity : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "infinity"

    override fun getMinPower(level: Int): Int = 20

    override fun getMaxPower(level: Int): Int = 50

    override fun isTreasure(): Boolean = true

    override fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        InfinityTiller.trigger(world, player, hoe, pos)
    }
}