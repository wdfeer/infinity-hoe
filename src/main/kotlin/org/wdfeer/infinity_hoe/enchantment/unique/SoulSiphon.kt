package org.wdfeer.infinity_hoe.enchantment.unique

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.GrowthAcceleration.Companion.growthAccelerationTick
import org.wdfeer.infinity_hoe.util.DamageSourceHelper
import org.wdfeer.infinity_hoe.util.hasEnchantment

class SoulSiphon : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "soul_siphon"
    override fun getMinPower(level: Int): Int = 15
    override fun getMaxPower(level: Int): Int = 50
    override fun canAccept(other: Enchantment?): Boolean = other !is GrowthAcceleration

    init {
        ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
    }

    private val interval: Int = 100
    private fun canIteratePlayers(world: World): Boolean = world.time % interval == 0L

    private fun onWorldTick(world: ServerWorld) {
        if (canIteratePlayers(world))
            for (player in world.players){
                if (!player.isAlive) continue
                if (player.handItems.none { it.hasEnchantment(this) }) continue

                player.damage(DamageSourceHelper.create(world, DamageTypes.MAGIC), 2f)
                growthAccelerationTick(world, player, 4)
            }
    }
}