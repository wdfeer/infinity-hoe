package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import net.minecraft.util.math.Vec3d
import org.joml.Vector3f
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks
import java.util.*
import kotlin.collections.ArrayDeque

object LunaDial : UsableHarvestChargeEnchantment(), PlayerTicker {
    private const val POSITION_SAVE_INTERVAL = 20
    private const val POSITIONS_STORED = 10

    // Vector3f instead of Vec3d to conserve Memory
    private val playerPositions: MutableMap<UUID, ArrayDeque<Vector3f>> = mutableMapOf()

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % POSITION_SAVE_INTERVAL == 0L;

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        if (player.inventoryStacks.any { it.item is HoeItem && it.hasEnchantment(this)}) recordPosition(player)
        else playerPositions.remove(player.uuid)
    }

    private fun recordPosition(player: ServerPlayerEntity) {
        val array = playerPositions[player.uuid] ?: ArrayDeque<Vector3f>().also { playerPositions[player.uuid] = it }
        array.addLast(player.entityPos.toVector3f())
        if (array.size > POSITIONS_STORED) array.removeFirst()
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean =
        playerPositions[player.uuid]?.let {
            if (it.isEmpty()) null
            else it
        }?.first()?.run {
            Vec3d(x.toDouble(), y.toDouble(), z.toDouble())
        }?.let {
            player.teleport(it.x, it.y, it.z, true)
            true
        } ?: false

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 50
    override fun chargeToString(charge: Int): String = "%.2f".format(charge.toFloat() / getChargeDecrement())

    override fun getTooltipColor(): Formatting = Formatting.DARK_AQUA

    override fun getPath(): String = "luna_dial"
}