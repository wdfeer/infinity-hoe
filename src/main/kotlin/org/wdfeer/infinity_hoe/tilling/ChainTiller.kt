package org.wdfeer.infinity_hoe.tilling

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

// Server-wide object that tills valid blocks automatically after a hoe with infinity enchantment has been used
object ChainTiller {
    fun initialize() {
        ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
    }

    private var worldActions: MutableMap<World, MutableList<ChainTillAction>> = mutableMapOf()

    fun preTrigger(world: World, stack: ItemStack, blockPos: BlockPos) { // Executed before the initial block is tilled
        if (world.isClient) return

        blockTypes[stack] = world.getBlockState(blockPos).block
    }

    private val blockTypes: MutableMap<ItemStack, Block> = mutableMapOf()

    fun trigger(world: World, hoe: ItemStack, pos: BlockPos, player: ServerPlayerEntity) { // Executed after the initial block is tilled
        if (!blockTypes.contains(hoe)) return
        val blockFilter: Block = blockTypes[hoe]!!
        blockTypes.remove(hoe)

        if (!worldActions.contains(world)) worldActions[world] = mutableListOf()

        worldActions[world]!!.add(ChainTillAction(world, hoe, player, pos, blockFilter))
    }

    private fun onWorldTick(world: World) {
        if (worldActions.contains(world) && worldActions[world] != null)
        {
            worldActions.forEach { it.value.forEach { action -> action.tick() }}
            worldActions = worldActions.mapValues { it.value.filter { action -> !action.isDead() }.toMutableList() }.toMutableMap()
        }
    }
}