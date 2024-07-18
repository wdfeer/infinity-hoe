package org.wdfeer.infinity_hoe

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


typealias Worlds = MutableMap<World, Chains>
typealias Chains = MutableMap<BlockPos, TillingBlock>

// block count left, hoe
typealias TillingBlock = Pair<Int, ItemStack>


// Server-wide object that tills valid blocks automatically after a hoe with infinity enchantment has been used
object ChainTiller {
    fun initialize() {
        ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
    }

    private val worlds: Worlds = mutableMapOf()

    private const val INITIAL_BLOCK_COUNT: Int = 16

    fun preTrigger(world: World, stack: ItemStack, blockPos: BlockPos) { // Executed before the initial block is tilled
        if (world.isClient) return

        blockTypes[stack] = world.getBlockState(blockPos).block
    }

    private val blockTypes: MutableMap<ItemStack, Block> = mutableMapOf()

    fun trigger(world: World, hoe: ItemStack, pos: BlockPos) { // Executed after the initial block is tilled
        if (!blockTypes.contains(hoe)) return
        val blockFilter: Block = blockTypes[hoe]!!
        blockTypes.remove(hoe)

        if (!worlds.contains(world)) worlds[world] = mutableMapOf()

        val next = getNext(world, pos, blockFilter)
        if (next != null) {
            worlds[world]!![next] = TillingBlock(INITIAL_BLOCK_COUNT, hoe)
        }
    }

    private fun onWorldTick(world: World) {
        if (worlds.contains(world) && worlds[world] != null)
        {
            chainsTick(world, worlds[world]!!)
        }
    }

    private fun chainsTick(world: World, chains: Chains) {
        val newChains: Chains = mutableMapOf()
        for (entry in chains) {
            val pos: BlockPos = entry.key
            val tillingBlock: TillingBlock = entry.value

            val next: BlockPos? = getNext(world, pos)
            setFarmland(world, pos)

            val hoe: ItemStack = tillingBlock.second
            if (hoe.isEmpty) continue
            hoe.damage++ // TODO: make the hoe actually break

            if (next != null)
                newChains[next] = TillingBlock(tillingBlock.first - 1, tillingBlock.second)
        }
        worlds[world] = newChains
    }

    private fun setFarmland(world: World, pos: BlockPos) {
        world.setBlockState(pos, Blocks.FARMLAND.defaultState)
    }

    private fun getNext(world: World, origin: BlockPos, blockFilter: Block? = null): BlockPos? {
        val originalBlockType: Block = blockFilter ?: world.getBlockState(origin).block

        val options: MutableList<BlockPos> = mutableListOf()
        for (x in -1..1) {
            for (z in -1..1) {
                val pos = origin.add(x, 0, z)

                if (pos.equals(origin)) continue

                if (world.getBlockState(pos).block == originalBlockType)
                    options.add(pos)
            }
        }

        return if (options.isEmpty()) null else options.random()
    }
}

