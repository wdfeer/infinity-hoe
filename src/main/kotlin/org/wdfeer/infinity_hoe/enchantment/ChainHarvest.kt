package org.wdfeer.infinity_hoe.enchantment

import com.google.common.math.IntMath.pow
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.event.CropBreakListener
import org.wdfeer.infinity_hoe.util.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class ChainHarvest : HoeEnchantment(Rarity.RARE) {
    companion object {
        fun initialize() {
            ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
        }

        private fun trigger(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, blockType: CropBlock, level: Int) {
            if (serverActions[world] == null)
                serverActions[world] = mutableListOf()
            else if (serverActions[world]!!.any { it.blocks.contains(pos) })
                return

            val applicable = getNext(pos, world, blockType)
            if (applicable.isEmpty()) return

            val power = getPower(level)

            serverActions[world]!!.add(ChainHarvestAction(applicable, power, blockType, player))
        }

        private fun getNext(
            pos: BlockPos,
            world: ServerWorld,
            blockType: CropBlock
        ) = pos.getAdjacentHorizontally(1).filter { isHarvestable(world, it, blockType) }


        private fun isHarvestable(world: ServerWorld, pos: BlockPos, cropType: CropBlock): Boolean {
            val state = world.getBlockState(pos)
            return state.block == cropType && cropType.getAge(state) == cropType.maxAge
        }

        private val serverActions: MutableMap<ServerWorld, MutableList<ChainHarvestAction>> = mutableMapOf()

        private data class ChainHarvestAction(
            var blocks: List<BlockPos>,
            var power: Int,
            val cropType: CropBlock,
            val player: ServerPlayerEntity
        )

        private fun canPlayerHarvest(player: ServerPlayerEntity) = player.isAlive

        private fun onWorldTick(world: ServerWorld) {
            val worldActions = serverActions[world] ?: return
            worldActions.removeIf { it.power <= 0 }

            for (i in 0 until  worldActions.count()) {
                val action = worldActions[i]

                if (action.power <= 0) continue

                if (!canPlayerHarvest(action.player)) continue

                val newBlocks: MutableList<BlockPos> = mutableListOf()
                for (pos in action.blocks) {
                    if (action.power <= 0) continue

                    if (isHarvestable(world, pos, action.cropType)){
                        harvest(world, pos, action.player)
                        action.power--
                    }

                    newBlocks.addAll(getNext(pos, world, action.cropType)
                        .filter { !action.blocks.contains(it) && !newBlocks.contains(it) })
                }
                action.blocks = newBlocks
            }
        }

        private fun harvest(world: ServerWorld, pos: BlockPos, player: ServerPlayerEntity) {
            val state = world.getBlockState(pos)
            world.breakBlock(pos, true, player)
            CropBreakListener.onCropBreak(world, player, pos, state, ModEnchantments.chainHarvest)
        }

        private fun getPower(level: Int) = pow(4, level)
    }
    
    override fun getPath(): String = "chain_harvest"

    override fun getMaxLevel(): Int = 3

    override fun getMinPower(level: Int): Int = 18 + level * 6

    override fun getMaxPower(level: Int): Int = 24 + level * 6

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) { 
        trigger(world, player, pos, state.block as CropBlock, hoe.getEnchantmentLevel(this))
    }
}