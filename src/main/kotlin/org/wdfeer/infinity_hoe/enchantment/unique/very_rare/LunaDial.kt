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

class LunaDial : UsableHarvestChargeEnchantment(Rarity.VERY_RARE), PlayerTicker {
    companion object {
        const val POSITIONS_STORED = 200

        // Vector3f instead of Vec3d to conserve RAM
        val playerPositions: MutableMap<UUID, ArrayDeque<Vector3f>> = mutableMapOf()
    }

    override fun canIteratePlayers(world: ServerWorld): Boolean = true

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        if (player.inventoryStacks.any { it.item is HoeItem && it.hasEnchantment(this)})
            recordPosition(player)
        else
            playerPositions.remove(player.uuid)
    }

    private fun recordPosition(player: ServerPlayerEntity) {
        val array = playerPositions[player.uuid] ?: ArrayDeque<Vector3f>().also { playerPositions[player.uuid] = it }
        array.addLast(player.pos.toVector3f())
        if (array.size > POSITIONS_STORED)
            array.removeFirst()
    }

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean =
        playerPositions[player.uuid]?.run {
            if (isEmpty()) null
            else this
        }?.first()?.run {
            Vec3d(x.toDouble(), y.toDouble(), z.toDouble())
        }?.let {
            player.startFallFlying()
            player.setPosition(it)
            true
        } ?: false

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 16
    override fun getChargeDecrement(): Int = 50
    override fun chargeToString(charge: Int): String = "%.2f".format(charge.toFloat() / getChargeDecrement())

    override fun getTooltipColor(): Formatting = Formatting.DARK_AQUA

    override fun getPowerRange(level: Int): IntRange = 30..100

    override fun getPath(): String = "luna_dial"
}