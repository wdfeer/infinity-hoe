package org.wdfeer.infinity_hoe

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


typealias Worlds = MutableMap<World, Chains>
typealias Chains = MutableMap<BlockPos, BlockData>

// block count left, hoe, player
typealias BlockData = Triple<Int, ItemStack, ServerPlayerEntity>


// Server-wide object that tills valid blocks automatically after a hoe with infinity enchantment has been used
object ChainTiller {
    fun initialize() {
        ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
    }

    private val worlds: Worlds = mutableMapOf()

    fun preTrigger(world: World, stack: ItemStack, blockPos: BlockPos) { // Executed before the initial block is tilled
        if (world.isClient) return

        blockTypes[stack] = world.getBlockState(blockPos).block
    }

    private val blockTypes: MutableMap<ItemStack, Block> = mutableMapOf()

    fun trigger(world: World, hoe: ItemStack, pos: BlockPos, player: ServerPlayerEntity) { // Executed after the initial block is tilled
        if (!blockTypes.contains(hoe)) return
        val blockFilter: Block = blockTypes[hoe]!!
        blockTypes.remove(hoe)

        if (!worlds.contains(world)) worlds[world] = mutableMapOf()

        val next = getNext(world, pos, blockFilter)
        if (next != null) {
            worlds[world]!![next] = BlockData(getBlockCount(hoe), hoe, player)
        }
    }

    private fun getBlockCount(hoe: ItemStack): Int {
        return (hoe.item.maxDamage - hoe.damage) / 4 + 1
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
            val data: BlockData = entry.value

            val next: BlockPos? = getNext(world, pos)
            setFarmland(world, pos)

            val hoe: ItemStack = data.second
            if (hoe.isEmpty) continue
            damageHoe(hoe, data.third)

            if (next != null && data.first > 1)
                newChains[next] = BlockData(data.first - 1, data.second, data.third)
        }
        worlds[world] = newChains
    }

    private fun damageHoe(hoe: ItemStack, player: ServerPlayerEntity) {
        hoe.damage(1, player) { p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }

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

