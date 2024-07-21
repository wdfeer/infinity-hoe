package org.wdfeer.infinity_hoe.enchantment

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.chain.InfinityTillAction
import org.wdfeer.infinity_hoe.enchantment.common.HoeEnchantment

class Infinity : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "infinity"

    override fun getMinPower(level: Int): Int = 20

    override fun getMaxPower(level: Int): Int = 50

    override fun isTreasure(): Boolean = true

    override fun onTill(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) {
        trigger(world, player, hoe, pos)
    }

    companion object {
        fun initialize() {
            ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
        }

        private var worldActions: MutableMap<World, MutableList<InfinityTillAction>> = mutableMapOf()

        fun preTrigger(world: World, stack: ItemStack, blockPos: BlockPos) { // Executed before the initial block is tilled
            if (world.isClient) return

            blockTypes[stack] = world.getBlockState(blockPos).block
        }

        private val blockTypes: MutableMap<ItemStack, Block> = mutableMapOf()

        fun trigger(world: World, player: ServerPlayerEntity, hoe: ItemStack, pos: BlockPos) { // Executed after the initial block is tilled
            if (!blockTypes.contains(hoe)) return
            val blockFilter: Block = blockTypes[hoe]!!
            blockTypes.remove(hoe)

            if (!worldActions.contains(world)) worldActions[world] = mutableListOf()

            worldActions[world]!!.add(InfinityTillAction(world, hoe, player, pos, blockFilter))
        }

        private fun onWorldTick(world: World) {
            if (worldActions.contains(world) && worldActions[world] != null)
            {
                worldActions.forEach { it.value.forEach(InfinityTillAction::tick) }
                worldActions = worldActions.mapValues { it.value.filter { action -> !action.isDead() }.toMutableList() }.toMutableMap()
            }
        }
    }
}