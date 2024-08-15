package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener

class Blazing : HoeEnchantment(Rarity.VERY_RARE), AirUseListener {
    override fun getPowerRange(level: Int): IntRange = 20..70

    override fun getPath(): String = "blazing"

    override fun onUseInAir(world: ServerWorld, player: ServerPlayerEntity, stack: ItemStack) {
        TODO("Not yet implemented")
    }
}