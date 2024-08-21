package org.wdfeer.infinity_hoe.enchantment.demeter

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

abstract class DemeterEnchantment : HoeEnchantment(Rarity.UNCOMMON) {
    override fun getPowerRange(level: Int): IntRange = 10 ..50

    fun getPlayerHarvestCount(world: ServerWorld, player: ServerPlayerEntity): Int {
        val playerData = DemeterState.getServerState(world.server, false)[player.uuid] ?: return 0
        return playerData.values.sum()
    }
}