package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.chain.CalciumBurstAction
import org.wdfeer.infinity_hoe.enchantment.chain.ChainEnchantment
import org.wdfeer.infinity_hoe.event.listener.Useable

class CalciumBurst : ChainEnchantment<CalciumBurstAction>(Rarity.UNCOMMON), Useable {
    override fun getPowerRange(level: Int): IntRange = 13..50

    override fun getPath(): String = "calcium_burst"

    override fun use(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        TODO("Not yet implemented")
    }
}