package org.wdfeer.infinity_hoe

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import kotlin.math.pow


typealias Worlds = MutableMap<World, Chains>
typealias Chains = MutableMap<BlockPos, BlockData>

// block count left (later - power), hoe, player
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

        val power: Int = getPower(hoe)
        val next = getNext(world, pos, power, blockFilter)
        if (next.first.isNotEmpty()) {
            for (position in next.first) {
                worlds[world]!![position] = BlockData(next.second, hoe, player)
            }
        }
    }

    private fun getPower(hoe: ItemStack): Int =
        (((hoe.item.maxDamage - hoe.damage) + 1f) * (hoe.getEnchantmentLevel(Enchantments.UNBREAKING) + 1f))
            .pow(1.5f).toInt()

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
            val originalBlockType: Block = world.getBlockState(pos).block

            setFarmland(world, pos)

            val hoe: ItemStack = data.second
            if (hoe.isEmpty) continue
            damageHoe(hoe, data.third)

            if (data.first <= 1) continue

            val (nextPositions, power) = getNext(world, pos, data.first - 1, originalBlockType)

            if (nextPositions.isNotEmpty())
                for (p in nextPositions) {
                    newChains[p] = BlockData(power, data.second, data.third)
                }
        }

        worlds[world] = newChains
    }

    private fun damageHoe(hoe: ItemStack, player: ServerPlayerEntity) {
        hoe.damage(1, player) { p -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
    }

    private fun setFarmland(world: World, pos: BlockPos) {
        world.setBlockState(pos, Blocks.FARMLAND.defaultState)
    }

    private fun getNext(world: World, origin: BlockPos, power: Int, blockFilter: Block? = null): Pair<List<BlockPos>, Int> {
        val originalBlockType: Block = blockFilter ?: world.getBlockState(origin).block

        fun getNeighbors(distance: Int): List<BlockPos> {
            val positions: MutableList<BlockPos> = mutableListOf()
            for (x in -distance..distance) {
                for (z in -distance..distance) {
                    val pos = origin.add(x, 0, z)

                    if (pos.equals(origin) || worlds[world]!!.contains(pos)) continue

                    if (world.getBlockState(pos).block == originalBlockType)
                        positions.add(pos)
                }
            }
            return positions
        }

        var next = getNeighbors(1)
        if (next.isEmpty()) next = getNeighbors(2)

        val count = when {
            power % 2 == 0 && next.count() > 1 -> {
                2
            }
            else -> {
                1
            }
        }
        val newPower: Int = power / count

        return Pair(next.shuffled().take(count), newPower)
    }
}