package org.wdfeer.infinity_hoe.enchantment.chain

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

class CalciumBurstAction(
    world: ServerWorld,
    hoe: ItemStack,
    player: ServerPlayerEntity,
    origin: BlockPos
) : ChainAction(world, hoe, player, origin) {
    override fun processBlock(pos: BlockPos) {
        TODO("Not yet implemented")
    }

    override fun getRequiredBlock(): Block {
        TODO("Not yet implemented")
    }

    override fun getEnchantment(): HoeEnchantment {
        TODO("Not yet implemented")
    }
}