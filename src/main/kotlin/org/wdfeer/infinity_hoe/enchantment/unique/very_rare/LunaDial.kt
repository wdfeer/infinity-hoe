package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import net.minecraft.util.math.Vec3d
import net.minecraft.world.dimension.DimensionType
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks
import java.util.*
import kotlin.collections.ArrayDeque

object LunaDial : UsableHarvestChargeEnchantment(), PlayerTicker {
    private const val POSITION_SAVE_INTERVAL = 20
    private const val POSITIONS_STORED = 30

    private data class PastPos(val pos: Vec3d, val pitch: Float, val yaw: Float, val dimension: DimensionType)

    private val playerPositions: MutableMap<UUID, ArrayDeque<PastPos>> = mutableMapOf()

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % POSITION_SAVE_INTERVAL == 0L;

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        if (player.inventoryStacks.any { it.item is HoeItem && it.hasEnchantment(this) }) recordPosition(player)
        else playerPositions.remove(player.uuid)
    }

    private fun recordPosition(player: ServerPlayerEntity) {
        val array = playerPositions.getOrPut(player.uuid) { ArrayDeque() }
        array.addLast(PastPos(player.pos, player.pitch, player.yaw, player.world.dimension))
        if (array.size > POSITIONS_STORED) array.removeFirst()
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean =
        playerPositions[player.uuid]?.run {
            if (isEmpty()) null
            else this
        }?.first()?.let { saved ->
            player.startFallFlying()
            player.teleport(
                world.server.worlds.find { it.dimension == saved.dimension },
                saved.pos.x,
                saved.pos.y,
                saved.pos.z,
                saved.pitch,
                saved.yaw
            )
            true
        } ?: false

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 50
    override fun chargeToString(charge: Int): String = "%.2f".format(charge.toFloat() / getChargeDecrement())

    override fun getTooltipColor(): Formatting = Formatting.DARK_AQUA

    override fun getPowerRange(level: Int): IntRange = 30..100

    override fun getPath(): String = "luna_dial"
}