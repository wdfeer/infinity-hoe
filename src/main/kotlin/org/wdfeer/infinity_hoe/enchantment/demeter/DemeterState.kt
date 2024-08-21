package org.wdfeer.infinity_hoe.enchantment.demeter

import net.minecraft.block.Block
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.InfinityHoe
import java.util.*

class DemeterState(private val players: MutableMap<UUID, MutableMap<Identifier, Int>>) : MutableMap<UUID, MutableMap<Identifier, Int>> by players, PersistentState() {
    constructor() : this(mutableMapOf())

    companion object {
        private const val NBT_KEY: String = "demeter_state"

        private fun createFromNbt(nbt: NbtCompound): DemeterState = DemeterState().apply {
            val map = nbt.getCompound(NBT_KEY)

            for (playerUUIDKey in map.keys) {
                val playerUUID = UUID.fromString(playerUUIDKey)
                val playerNbt = map.getCompound(playerUUIDKey)
                val harvestData = mutableMapOf<Identifier, Int>()

                for (cropIdKey in playerNbt.keys) {
                    val cropId = Identifier(cropIdKey)
                    val amount = playerNbt.getInt(cropIdKey)
                    harvestData[cropId] = amount
                }

                players[playerUUID] = harvestData
            }
        }

        fun getServerState(server: MinecraftServer): DemeterState {
            val persistentStateManager = server.getWorld(World.OVERWORLD)!!.persistentStateManager

            val state: DemeterState = persistentStateManager.getOrCreate(::createFromNbt, ::DemeterState, "${InfinityHoe.MOD_ID}:$NBT_KEY")

            state.markDirty()

            return state
        }

        fun incrementPlayerHarvestCount(world: ServerWorld, player: ServerPlayerEntity, crop: Block) {
            val playerData = getServerState(world.server).getOrPut(player.uuid) { mutableMapOf() }
            playerData.getOrPut(Registries.BLOCK.getId(crop)) {0}.inc()
        }
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        val map = NbtCompound().apply { forEach { playerUUID, harvestData ->
                val playerNbt = NbtCompound()
                for ((cropId, amount) in harvestData) {
                    playerNbt.putInt(cropId.toString(), amount)
                }
                put(playerUUID.toString(), playerNbt)
        } }

        return nbt.apply { put(NBT_KEY, map) }
    }
}