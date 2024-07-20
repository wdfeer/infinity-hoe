package org.wdfeer.infinity_hoe.enchantment

import com.google.common.math.IntMath.pow
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.util.getAdjacentHorizontally
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.hasEnchantment

class ChainHarvest : HoeEnchantment(Rarity.RARE) {
    companion object {
        fun initialize() {
            PlayerBlockBreakEvents.BEFORE.register { world, player, pos, state, _ -> preBlockBreak(world, player, pos, state); true }
            ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
        }

        private fun preBlockBreak(
            world: World?,
            player: PlayerEntity?,
            pos: BlockPos?,
            blockState: BlockState?,
        ) {
            if (world is ServerWorld && player is ServerPlayerEntity && pos != null && blockState?.block is CropBlock) {
                val stack = player.getStackInHand(player.activeHand)
                if (stack.item is HoeItem && stack.hasEnchantment(ModEnchantments.chainHarvest)){
                    trigger(world, player, pos, blockState.block as CropBlock, stack.getEnchantmentLevel(ModEnchantments.chainHarvest))
                }
            }
        }

        private fun trigger(world: ServerWorld, player: ServerPlayerEntity, pos: BlockPos, blockType: CropBlock, level: Int) {
            val power = getPower(level)
            val applicable = getNext(pos, world, blockType)
            if (applicable.isEmpty()) return

            if (serverActions[world] == null)
                serverActions[world] = mutableListOf()
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

            for (action in worldActions) {
                if (!canPlayerHarvest(action.player)) continue
                if (action.power <= 0) continue

                val newBlocks: MutableList<BlockPos> = mutableListOf()
                for (pos in action.blocks) {
                    if (isHarvestable(world, pos, action.cropType)){
                        world.breakBlock(pos, true, action.player)
                        action.power--
                    }

                    newBlocks.addAll(getNext(pos, world, action.cropType)
                        .filter { !action.blocks.contains(it) && !newBlocks.contains(it) })
                }
                action.blocks = newBlocks
            }

            worldActions.removeIf { it.power <= 0 }
        }

        private fun getPower(level: Int) = pow(4, level)
    }
    
    override fun getPath(): String = "chain_harvest"

    override fun getMaxLevel(): Int = 3

    override fun getMinPower(level: Int): Int = 18 + level * 6

    override fun getMaxPower(level: Int): Int = 24 + level * 6
}